# Randoli Assessment

## Architecture
This project was built with Hexagonal Architecture in order to isolate the domain rules, 
being able to plug in other data sources if it's necessary and execute tests isolated

## Technologies
- JDK 11
- Gradle 7.3.1
- Spring 2.6.1
- jUnit 5

# How to build
- Execute this command in project root `./gradlew clean build`

# How to run tests
- Execute this command in project root `./gradlew test`

## Important to run
- To Run the application on docker, you **MUST** have installed Docker and Docker-compose
- When you run it for the first time, the database will be empty. Therefore, you must use all POST endpoints to populate it

## How to run on docker
- First read this [topic](#important-to-run)
- Second you must [build](#how-to-build) the application
- After that you must run this command in project root `docker-compose up --build -d`
- The application will be available in `http://localhost:8080`

## Docs
- This project contains swagger, it can be accessed in `http://localhost:8080/docs.html` after you [run](#how-to-run-on-docker) the application
- This project also contains Postman Collection and Postman environment if you prefer import in your machine
