package com.TestingApp.TestingApp.repositories;

import com.TestingApp.TestingApp.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByEmail(String name);

}
