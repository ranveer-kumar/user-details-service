# User Details Service

This is a Spring Boot project for managing user details. 

## Table of Contents
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites
- Java (tested on 17 version)
- Maven

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/user-details-service.git
   cd user-details-service
   ```

2. Build the project
   ```bash
   mvn clean install # can be use wrapper ./mvnw clean install
   ```
3. Run the application:
   ```bash
    java -jar target/user-details-service-0.0.1-SNAPSHOT.jar
   ```
4. Open Swaagger URL:
    ```bash
   http://localhost:8080/swagger-ui.html
   ```
5. Test coverage report generation:
   
    ```bash
   mvn clean test # can be use wrapper ./mvnw clean install
   ``` 
### Access Test coverage report (jacoco plugin report)  
   [Test coverage report](./target/site/jacoco/index.html)

## Usage

### Endpoints

#### Health Check

- **Endpoint:** `/actuator/health`
- **Method:** `GET`
- **Description:** Check the health of the service

#### Get User Details by UserId

- **Endpoint:** `/api/userdetails/{userId}`
- **Method:** `GET`
- **Parameters:**
    - `userId` (integer, path, required) - Numeric ID of the user to get
- **Description:** Get user details by userId
#### cURL request sample:
```agsl
curl --location 'http://localhost:8080/api/userdetails/2'
```

#### Get User Details by First Name and Last Name

- **Endpoint:** `/api/userdetails/{firstName}/{lastName}`
- **Method:** `GET`
- **Parameters:**
    - `firstName` (string, path, required) - First name
    - `lastName` (string, path, required) - Last name
- **Description:** Get user details by first name and last name
##### cURL request sample:
```agsl
curl --location 'http://localhost:8080/api/userdetails/Emily/Jones'
```
#### Get Paginated User Details

- **Endpoint:** `/api/userdetails`
- **Method:** `GET`
- **Parameters:**
    - `pageNo` (integer, query, optional) - Page number (default: 0)
    - `offset` (integer, query, optional) - Offset of the page (default: 10)
- **Description:** Get paginated user details
##### cURL request sample:
```agsl
curl --location 'http://localhost:8080/api/userdetails?pageNo=0&offset=10'
```
  
#### Partially Update User Details by User ID

- **Endpoint:** `/api/userdetails/{empId}`
- **Method:** `PATCH`
- **Parameters:**
    - `empId` (integer, path, required) - Numeric ID of the user to update
- **Request Body:**
    - `title` (string, optional)
    - `firstName` (string, optional)
    - `lastName` (string, optional)
    - `gender` (string, optional)
- **Description:** Partially update user details by user ID
##### cURL request sample:
```agsl
curl --location --request PATCH 'http://localhost:8080/api/userdetails/2' \
--header 'Content-Type: application/json' \
--data '{
  "title": "test",
  "addresses": [
        {
            "addressId": 2,
            "street": "stree1"
        }
  ]
}'
```