# Workforce Management API

A backend service for managing staff tasks with features like priority, reassignment, and smart daily views.

## Tech Stack
- Java 17
- Spring Boot 3.0.4
- Gradle
- Lombok
- MapStruct (manual or custom mappers)
- In-memory data structures (no database)

## Features
- Create, View, Filter Tasks
- Assign/Reassign tasks to staff
- Update priority
- Smart task view (by date range)
- Add comments and activity logs

## Running the Project
1. Clone the repo
2. Open in Eclipse or IntelliJ
3. Run `WorkforceMgmtApplication.java`
4. Test endpoints using Postman

## Sample Endpoints
- `GET /tasks`
- `POST /tasks`
- `PUT /tasks/{id}/reassign?newStaffId=...`
- `PUT /tasks/{id}/priority?priority=HIGH`
- `POST /tasks/{id}/comment`
- `GET /tasks/staff/{staffId}?start=2025-08-03&end=2025-08-05`
