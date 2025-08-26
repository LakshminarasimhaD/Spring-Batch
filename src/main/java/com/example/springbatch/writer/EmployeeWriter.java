package com.example.springbatch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springbatch.entity.Employee;
import com.example.springbatch.repository.IEmployeesRepository;
import com.example.springbatch.service.EmailService;

@Component
public class EmployeeWriter implements ItemWriter<Employee> {
	
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);


    @Autowired
    private IEmployeesRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        List<? extends Employee> employees = chunk.getItems();
        
        // 1. Save the employees to the database
        employeeRepository.saveAll(employees);
        System.out.println("Saved " + employees.size() + " employees to the database.");
        logger.info("Saved {} employees to the database.", employees.size());

        // 2. Send a welcome email to each employee
        for (Employee employee : employees) {
            String subject = "Welcome to Our Company, " + employee.getName() + "!";
            String body = "Hello " + employee.getName() + ", Welcome! Your record has been successfully processed.";
            emailService.sendEmail(employee.getEmail(), subject, body);
        }
    }
}
