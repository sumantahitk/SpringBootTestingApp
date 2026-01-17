package com.TestingApp.TestingApp.services;

import com.TestingApp.TestingApp.dto.EmployeeDTO;
import com.TestingApp.TestingApp.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface EmployeeService {
    public EmployeeDTO getEmployeeById(Long empId);
    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee);
    public  EmployeeDTO updateEmployeeById(Long empId,EmployeeDTO employeeDTO);
    public void deleteEmployee(Long empId);

}
