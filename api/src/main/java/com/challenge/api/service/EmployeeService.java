package com.challenge.api.service;

import com.challenge.api.model.CreateEmployeeRequest;
import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer for employee operations.
 * Uses in-memory storage with ConcurrentHashMap for thread safety.
 * In a real application, this would interface with a database.
 */
@Slf4j
@Service
public class EmployeeService {

    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();

    public EmployeeService() {
        initializeMockData();
    }

    /**
     * Retrieves all employees.
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        log.info("Retrieving all employees. Total count: {}", employeeStore.size());
        return new ArrayList<>(employeeStore.values());
    }

    /**
     * Retrieves an employee by UUID.
     * @param uuid Employee UUID
     * @return Employee if found
     * @throws NoSuchElementException if employee not found
     */
    public Employee getEmployeeByUuid(UUID uuid) {
        log.info("Retrieving employee with UUID: {}", uuid);
        Employee employee = employeeStore.get(uuid);
        if (employee == null) {
            log.warn("Employee not found with UUID: {}", uuid);
            throw new NoSuchElementException("Employee not found with UUID: " + uuid);
        }
        return employee;
    }

    /**
     * Creates a new employee.
     * @param request Employee creation request
     * @return Newly created employee
     */
    public Employee createEmployee(CreateEmployeeRequest request) {
        log.info("Creating new employee: {} {}", request.getFirstName(), request.getLastName());

        // Generate UUID for new employee
        UUID newUuid = UUID.randomUUID();

        // Create new employee
        Employee newEmployee = new EmployeeImpl(
                newUuid,
                request.getFirstName(),
                request.getLastName(),
                request.getFirstName() + " " + request.getLastName(),
                request.getSalary(),
                request.getAge(),
                request.getJobTitle(),
                request.getEmail(),
                request.getContractHireDate(),
                null // No termination date for new employees
                );

        // Store employee
        employeeStore.put(newUuid, newEmployee);

        log.info("Successfully created employee with UUID: {}", newUuid);
        return newEmployee;
    }

    /**
     * Initialize mock data for demonstration purposes.
     * In a real application, this would load from a database.
     */
    private void initializeMockData() {
        log.info("Initializing mock employee data");

        // Create some sample employees
        Employee emp1 = new EmployeeImpl(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                "John",
                "Doe",
                "John Doe",
                75000,
                30,
                "Software Engineer",
                "john.doe@company.com",
                Instant.parse("2023-01-15T09:00:00Z"),
                null);

        Employee emp2 = new EmployeeImpl(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                "Jane",
                "Smith",
                "Jane Smith",
                85000,
                28,
                "Senior Software Engineer",
                "jane.smith@company.com",
                Instant.parse("2022-06-01T09:00:00Z"),
                null);

        Employee emp3 = new EmployeeImpl(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174002"),
                "Bob",
                "Johnson",
                "Bob Johnson",
                65000,
                35,
                "DevOps Engineer",
                "bob.johnson@company.com",
                Instant.parse("2023-03-10T09:00:00Z"),
                Instant.parse("2023-12-01T17:00:00Z") // Terminated employee
                );

        employeeStore.put(emp1.getUuid(), emp1);
        employeeStore.put(emp2.getUuid(), emp2);
        employeeStore.put(emp3.getUuid(), emp3);

        log.info("Initialized {} mock employees", employeeStore.size());
    }
}
