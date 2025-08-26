package com.example.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbatch.entity.Employee;
@Repository
public interface IEmployeesRepository extends JpaRepository<Employee, Long> {

}
