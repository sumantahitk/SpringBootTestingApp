package com.TestingApp.TestingApp.repositories;

import com.TestingApp.TestingApp.TestContainerConfiguration;
import com.TestingApp.TestingApp.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//        @DataJpaTest is a Spring Boot test slice used to test
//        JPA repositories with an in-memory database,
//        running each test in a rollbacked transaction
//        for isolation and speed.
//    DOESN'T NEED TO ADD  @DataJpaTest AND @AutoConfigureTestDatabase
@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)

class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void SetUp(){
        employee=Employee.builder()
                .id(1L)
                .name("Sumanta")
                .email("sum@gmail.com")
                .salary(100L)
                .build();

    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployeeList() {
//        Arrange, Given
            employeeRepository.save(employee);

//        Act, When
        List<Employee> employeeList=employeeRepository.findByEmail(employee.getEmail());

//        Assert, Then
        assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isNotEmpty();
    Assertions.assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
//        Given
            String email="fryirhf@gmail.com";
//        When
        List<Employee> employeeList=employeeRepository.findByEmail(email);
//        Then
        assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList).isEmpty();
    }

}