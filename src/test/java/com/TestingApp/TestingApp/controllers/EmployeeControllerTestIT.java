package com.TestingApp.TestingApp.controllers;

import com.TestingApp.TestingApp.TestContainerConfiguration;
import com.TestingApp.TestingApp.dto.EmployeeDTO;
import com.TestingApp.TestingApp.entities.Employee;
import com.TestingApp.TestingApp.repositories.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)

public class EmployeeControllerTestIT extends AbstractIntegrationTest{


    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee testEmployee;
    private EmployeeDTO testEmployeeDto;

    @BeforeEach
    void setup(){
        testEmployee=Employee.builder()
                .id(1L)
                .name("Sumanta")
                .email("sumanta@gmail.com")
                .salary(100L)
                .build();
        testEmployeeDto=EmployeeDTO.builder()
                .id(1L)
                .name("Sumanta")
                .email("sumanta@gmail.com")
                .salary(100L)
                .build();
        employeeRepository.deleteAll();
    }
    @Test
    void getEmployeeById_Success() {
        Employee savedEmployee= employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail())
//                .value(employeeDTO -> {
//                    Assertions.assertThat(employeeDTO.getEmail())
//                            .isEqualTo(savedEmployee.getEmail());
//                    Assertions.assertThat(employeeDTO.getId())
//                            .isEqualTo(savedEmployee.getId());
//
//                })
                ;
    }
    @Test
    void getEmployeeById_failure(){
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testcreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        Employee savedEmployee=employeeRepository.save(testEmployee);

        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }
    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_ThenCreateEmployee(){
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.put()
                .uri("/employees/999")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void testUpdateEmployee_whenAttemptingToUpdateTheEmail_thenThrowException() {
        Employee savedEmployee=employeeRepository.save(testEmployee);
        testEmployeeDto.setName("Random_Name");
        testEmployeeDto.setEmail("random1@gmail.com");

        webTestClient.put()
                .uri("/employees/{id}",savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }
    @Test
    void testUpdateEmployee_whenEmployeeIdValid_thenUpdateEmployee() {
        Employee savedEmployee=employeeRepository.save(testEmployee);
        testEmployeeDto.setName("Random_Name");
        testEmployeeDto.setSalary(251L);

        webTestClient.put()
                .uri("/employees/{id}",savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        webTestClient.delete()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }
    @Test
    void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee() {
        Employee savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
    }


}