# **Contact Management Service**

## **Overview**
The **Contact Management Service** is a Spring Boot application designed to interact with the HubSpot API for managing contacts. It provides a RESTful API to create and manage contacts, demonstrating clean architecture principles, observability, and robust error handling.

This project was developed with a focus on:
- **Clean Code**: Following SOLID principles and Vertical Slice Architecture.
- **Observability**: Adding traceability and logging for better debugging and monitoring.
- **Ease of Use**: Simple setup and clear instructions for running the service locally.

---

## **Features**
- **Create Contact**: A REST endpoint to create a new contact in HubSpot.
- **Retrieve Contacts**: A REST endpoint to retrieve contacts from HubSpot.
---

## **Technologies Used**
- **HubSpot API**: For contact management.
- **Java 21**: For modern language features and performance.
- **Spring Boot 3.x**: For rapid application development.
- **Micrometer**: For distributed tracing and observability.
- **Maven Wrapper**: For dependency management and build automation.
- **JUnit/Mockito 5**: For unit and integration testing.
- **SLF4J/Logback**: For logging.

---

## **Project Structure**
This project is structurally built on the following architectural patterns:
- **Vertical Slice Architecture**
- **Hexagonal Architecture**
- **CQRS**

Where each feature (vertical slice) is self-contained and modular. The key components of a feature are:

- **Endpoint**: Handles HTTP requests and responses.
- **Command / Query**: Contains the input data.
- **Command Handler / Query Handler**: Handles orchestration and business logic.
- **Integration Adapter**: Communicates with the HubSpot API.

---

## **Setup Instructions**
Follow these steps to set up and run the application locally.

### **Prerequisites**
**Java 21**: Ensure you have Java 21 installed. You can verify this by running:
   ```bash
   java -version
   ```

### **Clone the Repository**
Clone the project repository to your local machine:
```bash
git clone https://github.com/gabrieljbo/hubspot-ta.git
cd hubspot-ta
```

### **Build the Project**
Run the following command to build the project using the Maven Wrapper:
```bash
./mvnw clean package
```

For Windows:
```bash
mvnw.cmd clean package
```

### **Run the Application**
Start the application using:
```bash
./mvnw spring-boot:run
```

For Windows:
```bash
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:6666`.

---

## **Usage**
### **Create Contact**
To create a new contact, send a POST request to the following endpoint:

**Endpoint**: `POST /hubspot-ta/api/v1/contacts`

**Request Body**:
```json
{
  "firstName": "Nia",
  "lastName": "Larson",
  "email": "nia.larson@hubteam.com"
}
```

**Response**:
- **Success**:
```json
{
  "data": {
    "contact": {
      "id": "95125225396",
      "createdAt": "2025-01-29T05:20:19.963Z",
      "properties": {
        "email": "nia.larson@hubteam.com",
        "firstname": "Nia",
        "lastname": "Larson"
      }
    }
  },
  "status": "SUCCESS"
}
```
- **Error**:
```json
{
  "message": "System error",
  "details": [
    {
      "subject": "CONFLICT",
      "message": "Contact already exists. Existing ID: 95125225396"
    }
  ],
  "status": "ERROR"
}
```

### **Retrieve Contacts**
To retrieve contacts, send a GET request to the following endpoint:

**Endpoint**: `GET /hubspot-ta/api/v1/contacts`

**Request Params**:
```bash
limit=50
offset=50
properties=firstname,email
```

**Response**:
- **Success**:
```json
{
  "data": {
    "contacts": [
      {
        "id": "95096722971",
        "createdAt": "2025-01-29T02:02:05.83Z",
        "properties": {
          "email": "jo.hu@hubteam.com",
          "firstname": "Jo",
          "lastname": "Hu"
        }
      },
      {
        "id": "94757673235",
        "createdAt": "2025-01-27T21:44:33.072Z",
        "properties": {
          "email": "billy.li@hubteam.com",
          "firstname": "Billy",
          "lastname": "Li"
        }
      }
    ]
  },
  "status": "SUCCESS"
}
```
- **Error**:
```json
{
  "message": "System error",
  "details": [
    {
      "subject": "CONFLICT",
      "message": "Error message"
    }
  ],
  "status": "ERROR"
}
```

---

## **Observability**
- **Traceability**: Each request is logged with a unique `traceId` and `spanId` for debugging and monitoring.
- **Logs**: Logs are written to the console and include detailed information about the application's execution.

Example log entry:
```
2025-01-29T00:29:06.406-05:00  INFO 11510 --- [hubspot-ta] [nio-6666-exec-5] [6799bca20f06ffa827c9d819594703e9-27c9d819594703e9] .l.h.c.f.c.v.CreateContactCommandHandler : Creating contact with the command -> CreateContactCommand(firstName=Nia, lastName=Larson, email=nia.larson@hubteam.com)
```

---

## **Testing**
The project includes integration tests to ensure the reliability of all critical features.

### **Run Tests**
To execute all tests, run:
```bash
./mvnw test
```

For Windows:
```bash
mvnw.cmd test
```

### **Test Coverage**
- **Integration Tests**: Test the end-to-end functionality of "Create Contact" and "Retrieve Contacts" features.

---

## **Error Handling**
The application gracefully handles errors from the HubSpot API and provides meaningful responses to the client. Common errors include:
- **409 Conflict**: Contact already exists.
- **500 Internal Server Error**: Unexpected errors.

---

## **Future Improvements**
- Add more integration tests with edge cases.
- Add caching for frequently accessed data.
- Implement security, compliance and QoS policies around APIs.
---

## **Contact**
If you have any questions or feedback, feel free to reach out:
- **Name**: Gabriel Buitrago
- **Email**: gabrieljbo@gmail.com
- **LinkedIn**: https://www.linkedin.com/in/gabrieljbo/

--- 
