# Person Management Service

## Overview

The Person Management Service is a comprehensive RESTful web application designed to manage and store personal information efficiently using Jakarta EE 10.
It utilizes an H2 In-Memory database for persistent storage, enabling rapid access and manipulation of personal records.
This service offers a range of functionalities from adding, updating, and deleting personal information to generating statistics about the data stored.

## System Requirements

- Java 17
- Maven
- Wildfly Server (Tested with Wildfly 31.0.1 Final)
- H2 In-Memory Database (Automatically integrated through Maven dependencies)

## Setup and Configuration

1. **Configure Wildfly Server**: Ensure the Wildfly server is installed and correctly configured for your environment.
2. **Build Project**: Execute `mvn clean generate-sources install -DskipTests` in the project's root directory to generate the `.war` file.
3. **Deployment**: Deploy the generated `.war` file on the Wildfly server.

## Key Features

- **RESTful API Endpoints**: Offers a suite of API endpoints for managing personal data including adding new records, retrieving all records, updating existing records, and deleting records based on
  unique identifiers.

- **XML Data Processing**: Supports XML formatted data for input and output, providing an easy and standardized way to exchange information.

- **Statistics Generation**: Generates and retrieves statistics about the personal data managed by the service, such as the total number of records, the number of valid and invalid add requests, and
  detailed error messages for invalid requests.

- **Data Validation and Error Handling**: Implements robust data validation and error handling mechanisms to ensure the integrity of the data and to provide meaningful feedback for correction.

- **In-Memory Database**: Utilizes H2 In-Memory Database for storing personal records, offering quick data retrieval and manipulation without the need for external database setup.

- **Secure and Scalable**: Designed with security and scalability in mind, ensuring that personal information is managed safely and the service can handle increasing loads efficiently.

## Endpoints

### Retrieve Persons

- **URL**: `/persons`
- **Method**: `GET`
- **Description**: Fetches a list of all stored persons.

### Add Person

- **URL**: `/persons/add`
- **Method**: `POST`
- **Description**: Adds a new person. Expects person data in XML format in the request body.

### Retrieve Statistics

- **URL**: `/persons/statistics`
- **Method**: `GET`
- **Description**: Provides statistics about the stored person data.

### Update Person

- **URL**: `/persons/{id}`
- **Method**: `PUT`
- **Description**: Updates the details of a single person. Expects person data in XML format in the request body. Use the person's ID in the URL to specify which person to update.

### Delete Person by ID

- **URL**: `/persons/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a person by their ID.

## Using the Postman Collection

A Postman collection is provided to facilitate testing of the project's endpoints.
Import the provided collection into Postman to easily send requests to the service and test its functionality.
[fullstack-backend-challenge.postman_collection_extended.json](fullstack-backend-challenge.postman_collection_extended.json)

## Contributors

- Christian Held

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
