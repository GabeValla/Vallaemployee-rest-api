Employee REST API - ReliaQuest Challenge Implementation

A secure REST API implementation for the ReliaQuest Entry-Level Java Challenge. This API serves as a bridge between an existing employee management system and the Employees-R-US SaaS platform.

Features

- **Three RESTful Endpoints**: Complete implementation of all required endpoints
- **Security**: HTTP Basic Authentication with role-based access
- **Validation**: Input validation with meaningful error messages

API Endpoints

### GET /api/v1/employee
Retrieves all employees in the system (unfiltered).

**Authentication**: Required (HTTP Basic Auth)
**Response**: List of all employees
**Status Codes**: 200 (OK), 401 (Unauthorized)

### GET `/api/v1/employee/{uuid}`
Retrieves a specific employee by their UUID.

**Authentication**: Required (HTTP Basic Auth)
**Path Parameter**: UUID of the employee
**Response**: Employee details or 404 if not found
**Status Codes**: 200 (OK), 401 (Unauthorized), 404 (Not Found)

### POST `/api/v1/employee`
Creates a new employee in the system.

**Authentication**: Required (HTTP Basic Auth)
**Request Body**: Employee creation request with validation
**Response**: Created employee with generated UUID
**Status Codes**: 201 (Created), 400 (Bad Request), 401 (Unauthorized), 500 (Internal Server Error)

##Authentication

The API uses HTTP Basic Authentication with the following credentials:

- **Username**: `webhook-user`
- **Password**: `webhook-secret-password`


Running the Application

### Prerequisites
- Java 17 or higher
- Git

Quick Start

1. **Clone the repository**:
   ```bash
   git clone <your-repository-url>
   cd entry-level-java-challenge
   ```

2. **Build the project**:
   ```bash
   ./gradlew build
   ```

3. **Run the application**:
   ```bash
   ./gradlew bootRun
   ```

4. **Access the API**:
   - Base URL: `http://localhost:8080/api/v1/employee`
   - Health Check: `http://localhost:8080/actuator/health`

Testing the API

Get All Employees
```bash
curl -u webhook-user:webhook-secret-password \
     -H "Content-Type: application/json" \
     http://localhost:8080/api/v1/employee
```
Get Employee by UUID
```bash
curl -u webhook-user:webhook-secret-password \
     -H "Content-Type: application/json" \
     http://localhost:8080/api/v1/employee/123e4567-e89b-12d3-a456-426614174000
```



Documentation

For detailed implementation documentation, architecture decisions, and technical details, see [IMPLEMENTATION_EXPLANATION.md](IMPLEMENTATION_EXPLANATION.md).

Development

Code Formatting
The project uses Spotless for code formatting:
```bash
./gradlew spotlessApply
```

Building
```bash
./gradlew build
```

Testing
```bash
./gradlew test
```

Security Considerations

- HTTP Basic Authentication for API access
- Input validation with Bean Validation annotations
- Proper error handling with appropriate HTTP status codes
- Comprehensive logging for audit trails

Future Enhancements

- Database integration with JPA/Hibernate
- JWT token authentication
- API documentation with Swagger/OpenAPI
- Comprehensive unit and integration tests
- Docker containerization
- Rate limiting and API throttling

