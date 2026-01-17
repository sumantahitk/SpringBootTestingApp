package com.TestingApp.TestingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder   // 5 usages
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String email;
    private String name;
    private Long salary;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmployeeDTO that)) return false;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getSalary());
    }
}
