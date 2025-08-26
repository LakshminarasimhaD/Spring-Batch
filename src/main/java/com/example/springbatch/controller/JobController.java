package com.example.springbatch.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springbatch.dto.EmployeeCsv;

@RestController
@RequestMapping("/spring-batch-api")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importEmployeeJob;
    
    @Autowired
    private FlatFileItemReader<EmployeeCsv> reader;

    @PostMapping("/import")
    public String importCsvToDB(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            // Create a temporary file
            File tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);
            
            // Set the resource for the ItemReader
            reader.setResource(new FileSystemResource(tempFile));

            JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(importEmployeeJob, jobParameters);

            return "Job is running: " + jobExecution.getJobId();
        } catch (Exception e) {
            return "Job failed to start: " + e.getMessage();
        }
    }
}
