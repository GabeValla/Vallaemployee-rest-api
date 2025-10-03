package com.challenge.api.controller;

import com.challenge.api.model.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Employee management operations.
 * Provides secure endpoints for webhook consumption by Employees-R-US platform.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Retrieves all employees in the system.
     * This endpoint returns all employees, unfiltered, for consumption by webhooks.
     *
     * @return List of all employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("GET /api/v1/employee - Retrieving all employees");
        List<Employee> employees = employeeService.getAllEmployees();
        log.info("Successfully retrieved {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    /**
     * Retrieves a single employee by their UUID.
     * This endpoint allows webhooks to fetch specific employee details.
     *
     * @param uuid Employee UUID (path variable)
     * @return Employee details if found
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Employee> getEmployeeByUuid(@PathVariable UUID uuid) {
        log.info("GET /api/v1/employee/{} - Retrieving employee by UUID", uuid);
        try {
            Employee employee = employeeService.getEmployeeByUuid(uuid);
            log.info("Successfully retrieved employee: {} {}", employee.getFirstName(), employee.getLastName());
            return ResponseEntity.ok(employee);
        } catch (NoSuchElementException e) {
            log.warn("Employee not found with UUID: {}", uuid);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new employee in the system.
     * This endpoint allows webhooks to create new employee records.
     *
     * @param request Employee creation request with all necessary attributes
     * @return Newly created employee if successful, error response otherwise
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        log.info("POST /api/v1/employee - Creating new employee: {} {}", request.getFirstName(), request.getLastName());
        try {
            Employee newEmployee = employeeService.createEmployee(request);
            log.info("Successfully created employee with UUID: {}", newEmployee.getUuid());
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
        } catch (Exception e) {
            log.error("Failed to create employee: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
