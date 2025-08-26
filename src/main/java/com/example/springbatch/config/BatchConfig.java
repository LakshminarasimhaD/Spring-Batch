package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.springbatch.dto.EmployeeCsv;
import com.example.springbatch.entity.Employee;
import com.example.springbatch.process.EmployeeProcessor;
import com.example.springbatch.writer.EmployeeWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	public FlatFileItemReader<EmployeeCsv> reader() {
		FlatFileItemReader<EmployeeCsv> reader = new FlatFileItemReader<>();
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames("employeeId", "name", "dob", "state", "departmentId", "designation", "salary",
								"email");
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
					{
						setTargetType(EmployeeCsv.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public EmployeeProcessor processor() {
		return new EmployeeProcessor();
	}

	@Bean
	public EmployeeWriter writer() {
		return new EmployeeWriter();
	}

	@Bean
	public Step importEmployeesStep(FlatFileItemReader<EmployeeCsv> reader, EmployeeProcessor processor,
			EmployeeWriter writer) {
		return new StepBuilder("importEmployeesStep", jobRepository)
				.<EmployeeCsv, Employee>chunk(5, transactionManager).reader(reader).processor(processor)
				.writer(writer).build();
	}//Reads → Processes → Writes employees in chunks of 100.

	@Bean
	public Job importEmployeeJob(Step importEmployeesStep) {
		return new JobBuilder("importEmployeeJob", jobRepository).incrementer(new RunIdIncrementer())
				.flow(importEmployeesStep).end().build();
	}//RunIdIncrementer ensures each run gets a new ID so you can re-run the job.

}