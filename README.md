# demo-foot-api


⚽ Football Teams Management API

📌 Overview:
This project is a RESTful API built with Spring Boot designed to manage football teams and their associated players.
It provides basic CRUD operations for teams, along with advanced features such as pagination and server-side sorting.
The application follows a layered architecture (Controller / Service / Repository) and uses DTOs for data transfer to ensure a clean separation of concerns.

🎯 Key Features

🏟 Team Management:
Create a new team (with or without players)
Retrieve all teams
Retrieve a team by its ID

📊 Data Access Features:
Pagination support for large datasets
Server-side sorting on:
Team name
Acronym
Budget

🧩 Business Rules:
All teams must have:
Name
Acronym
Budget
Players are optional at creation time
Input validation is handled at service layer
Custom exceptions ensure clear error handling

🏗 Architecture:
The project follows a standard Spring Boot layered architecture:
Controller → Service → Repository → Database

📦 Main Packages:
controller → REST endpoints
service → business logic
repository → data access layer (Spring Data JPA)
dto → data transfer objects
entity → JPA entities
exception → custom exception handling
mapper → manual mapping between Entity and DTO

🛠️ Tech Stack: Java 17, Spring Boot, Spring Web, Spring Data JPA, Hibernate, Maven, H2 / MySQL (configurable), JUnit 5, Mockito

📡 API Endpoints:
🔹 Retrieve all teams: GET /api/teams?page=0&size=10&sortBy=name

🔹 Retrieve a team by ID: GET /api/teams/{id}
🔹 Create a new team: POST /api/teams

Request Body
{
  "name": "Paris Saint-Germain",
  "acronym": "PSG",
  "budget": 1000000,
  "players": []
}

▶️ Running the Application:
1. Clone the repository: git clone https://github.com/your-username/demo-foot-api.git
2. Navigate to the project: cd demo-foot-api
3. Build and run: mvn clean install, mvn spring-boot:run

🧪 Testing:
Unit tests are implemented using: JUnit 5, Mockito
To run tests: mvn test

⚠️ Error Handling:
The application uses custom exceptions:
BadRequestException → invalid input or business rule violation
ResourceNotFoundException → entity not found

📌 Design Decisions: 
Manual mapping between Entity and DTO (no MapStruct)
Service layer handles validation logic
REST API designed following best practices
Clean separation of concerns





