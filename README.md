# Football Manager

**Football Manager** is a backend service for managing football teams, players, and transfers.  
It provides a REST API to perform CRUD operations on teams and players, and execute transfers including commission calculations.

---

## Features

- **Team Management:** Create, update, and delete football clubs.
- **Player Management:** Add players to teams.
- **Transfers:** Process player transfers between teams with commission logic.
- **Database Migration:** Automatic schema management using Liquibase.
- **REST API:** Fully documented endpoints.
- **CI/CD:** Prepare and deploy project on AWS 

---

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA** (Hibernate)
- **PostgreSQL**
- **Liquibase**
- **CI/CD(GitHub Action)**
- **Docker**

---

## Setup
To run this project, install it locally :
* You must have postgreSql with empty database.
* Clone this repository.
* Change dabase configure in properties file(password, url for your credentials).
* Run project.
* After that you can manage teams and their details(liquibase will insert data and create table).
* Link for postman requests(
https://cloudy-satellite-762388.postman.co/workspace/Team-Workspace~7895ee88-72d3-4a19-84ac-af5af6274dd5/collection/20439646-bb8f9d19-6e84-435a-bf05-5f64556f71b0?action=share&creator=20439646)
