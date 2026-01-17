package com.TestingApp.TestingApp.services.Imple;

import com.TestingApp.TestingApp.dto.EmployeeDTO;
import com.TestingApp.TestingApp.entities.Employee;
import com.TestingApp.TestingApp.exception.ResourceNotFoundException;
import com.TestingApp.TestingApp.repositories.EmployeeRepository;
import com.TestingApp.TestingApp.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    // 1️⃣ CREATE EMPLOYEE
    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDto) {

        log.info("Creating new employee with email: {}", employeeDto.getEmail());

        List<Employee> existingEmployees =
                employeeRepository.findByEmail(employeeDto.getEmail());

        if (!existingEmployees.isEmpty()) {
            log.error("Employee already exists with email: {}", employeeDto.getEmail());
            throw new RuntimeException(
                    "Employee already exists with email: " + employeeDto.getEmail());
        }

        Employee newEmployee =
                modelMapper.map(employeeDto, Employee.class);

        Employee savedEmployee =
                employeeRepository.save(newEmployee);

        log.info("Successfully created new employee with id: {}", savedEmployee.getId());

        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }


    // 2️⃣ GET EMPLOYEE BY ID
    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        log.info("Fetching employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        log.info("Successfully fetched employee with id: {}", id);

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    // 3️⃣ UPDATE EMPLOYEE
    @Override
    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDto) {

        log.info("Updating employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException(
                            "Employee not found with id: " + id);
                });

        if (!employee.getEmail().equals(employeeDto.getEmail())) {
            log.error("Attempted to update email for employee with id: {}", id);
            throw new RuntimeException("The email of the employee cannot be updated");
        }

        modelMapper.map(employeeDto, employee);
        employee.setId(id);

        Employee savedEmployee = employeeRepository.save(employee);

        log.info("Successfully updated employee with id: {}", id);

        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }


    // 4️⃣ DELETE EMPLOYEE
    @Override
    public void deleteEmployee(Long id) {

        log.info("Deleting employee with id: {}", id);

        boolean exists = employeeRepository.existsById(id);
        if (!exists) {
            log.error("Employee not found with id: {}", id);
            throw new ResourceNotFoundException(
                    "Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);

        log.info("Successfully deleted employee with id: {}", id);
    }
}
