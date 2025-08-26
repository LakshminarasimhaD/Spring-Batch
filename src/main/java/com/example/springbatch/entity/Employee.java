package com.example.springbatch.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "SPRING_BATCH_EMPLOYEES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String employeeId;
	@Column(length = 50)
    private String name;
    private LocalDate dob;
	@Column(length = 50)
    private String state;
    private String departmentId;
    @Column(length = 50)
    private String designation;
    private Double salary;
    private String email;
}
