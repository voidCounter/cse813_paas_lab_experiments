# CSE813 PaaS Lab Experiments

[![Index deployment](https://github.com/voidCounter/cse813_paas_lab_experiments/actions/workflows/deploy-client.yml/badge.svg)](https://github.com/voidCounter/cse813_paas_lab_experiments/actions/workflows/deploy-client.yml)
[![Service deployments](https://github.com/voidCounter/cse813_paas_lab_experiments/actions/workflows/service-cd.yml/badge.svg)](https://github.com/voidCounter/cse813_paas_lab_experiments/actions/workflows/service-cd.yml)

This project contains a collection of Spring Boot microservices developed for the CSE813 Cloud Computing Lab. Each service is designed to be independently deployable and scalable. Visit at [https://cse813-paas-experiments.pages.dev/](https://cse813-paas-experiments.pages.dev/) to access the deployed services.

## Services

The following services are included in this project:

*   **Matrix Multiplication (`matrix_multiplication`)**: A service for performing matrix multiplication.
*   **N Even Numbers (`n_even`)**: A service to generate the first N even numbers.
*   **Nth Largest Number (`nth_largest`)**: A service to find the Nth largest number in a given list.
*   **User Registration (`user_registration`)**: A service for user registration.
*   **User Validation (`user_validation`)**: A service for validating user credentials.
 
## Running the Services Locally

Each service can be run locally using Maven or with Docker.

### Prerequisites

*   Java Development Kit (JDK) 17 or later
*   Apache Maven
*   Docker (for Docker-based deployment)

### Running Locally with Maven

To run any service locally, navigate to its respective directory and use the Maven Spring Boot plugin:

```bash
# Example for matrix_multiplication service
cd matrix_multiplication
./mvnw spring-boot:run
```

Replace `matrix_multiplication` with the directory name of the service you want to run (e.g., `n_even`, `nth_largest`, `user_registration`, `user_validation`).

For `user_registration` and `user_validation` services, you will need a `.env` file in the service's root directory with the following content:

```plaintext
DB_URL=jdbc:postgresql://localhost:5432/your_database_name
DB_USER=your_database_username
DB_PASS=your_database_password
```

The service will typically be available at `http://localhost:8080`. Check the `application.properties` file within each service's `src/main/resources` directory for the specific port number if it differs.

### Running All Services with Docker Compose
#### Prerequisites
Again, make sure you have the `.env` at the root of the project with the necessary database configurations for the `user_registration` and `user_validation` services.
```plaintext
DB_URL=jdbc:postgresql://db:5432/your_database_name
DB_USER=your_database_username
DB_PASS=your_database_password
```
#### Using Docker Compose
A `docker-compose.yml` file is provided at the root of the project to build and run all services simultaneously. This is the easiest way to get all services running locally.

1.  **Ensure Docker is running.**
2.  **Navigate to the root directory of the project.**
3.  **Run Docker Compose:**

    ```bash
    docker-compose up --build
    ```
    If you want to run them in the background, use `docker-compose up -d --build`.

    The services will be accessible on the following ports by default (due to port conflicts if all ran on 8080 internally):
    *   Matrix Multiplication: `http://localhost:8081`
    *   N Even Numbers: `http://localhost:8082`
    *   Nth Largest Number: `http://localhost:8083`
    *   User Registration: `http://localhost:8084`
    *   User Validation: `http://localhost:8085`

    If you want to use the latest images from Docker Hub instead of building them locally, you can run:

    ```bash 
    docker-compose up -d
    ```

4.  **To stop all services:**
    ```bash
    docker-compose down
    ```

## CI/CD

This project uses GitHub Actions for Continuous Deployment.
-   **Deploy services to Render**: On every push to the `main` branch, the services are packaged as Docker images, pushed to Docker Hub, and then deployed to Render using deploy hooks. The specific deploy hook URL for each service is stored as a GitHub Secret (e.g., `CSE813_PAAS_N_EVEN_DEPLOY_HOOK`).
-   **Deploy experiment index**: The `experiment_index` service is also deployed to Cloudflare, allowing users to access a simple HTML page that links to the various services.

## Project Structure

Each service is a self-contained Spring Boot application located in its own directory (e.g., `matrix_multiplication/`, `n_even/`, etc.).

-   `pom.xml`: Maven project configuration.
-   `Dockerfile`: Instructions for building the Docker image.
-   `src/main/java`: Java source code.
-   `src/main/resources`: Application properties, static files, and templates.

The `experiment_index/` directory contains a simple HTML page that links to the deployed services.
