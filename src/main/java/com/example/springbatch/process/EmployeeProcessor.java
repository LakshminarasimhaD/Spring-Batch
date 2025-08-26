package com.example.springbatch.process;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;

import com.example.springbatch.dto.EmployeeCsv;
import com.example.springbatch.entity.Employee;

public class EmployeeProcessor implements ItemProcessor<EmployeeCsv, Employee> {
    
    @Override
    public Employee process(EmployeeCsv employeeCsv) throws Exception {
        // 1. Validate email format
        if (!isValidEmail(employeeCsv.getEmail())) {
            return null; // Skip invalid record
        }

        // 2. Validate age > 18
       LocalDate dob = LocalDate.parse(employeeCsv.getDob(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age < 18) {
            return null; // Skip if under 18
        }
        
        // Return a new valid Employee entity
        Employee employee = new Employee();
        employee.setEmployeeId(employeeCsv.getEmployeeId());
        employee.setName(employeeCsv.getName());
        employee.setDob(dob);
        employee.setState(employeeCsv.getState());
        employee.setDepartmentId(employeeCsv.getDepartmentId());
        employee.setDesignation(employeeCsv.getDesignation());
        employee.setSalary(Double.parseDouble(employeeCsv.getSalary()));
        employee.setEmail(employeeCsv.getEmail());
        return employee; // Correct record to be saved
    }

    private boolean isValidEmail(String email) {
        // Simple regex or a more robust library
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }
}
