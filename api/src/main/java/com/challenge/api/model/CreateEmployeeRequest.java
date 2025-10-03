package com.challenge.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for creating new employees.
 * Includes validation annotations for input validation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Salary is required") @Positive(message = "Salary must be positive") private Integer salary;

    @NotNull(message = "Age is required") @Positive(message = "Age must be positive") private Integer age;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Contract hire date is required") private Instant contractHireDate;
}
