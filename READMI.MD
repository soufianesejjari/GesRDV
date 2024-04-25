# Project Architecture

This project is a Java Spring Boot application that provides a RESTful API for managing appointments (RendezVous), health centers (CentreSante), time slots (Creneau), and users (Utilisateur). The application also includes authentication and authorization features.

## Architectural Strategies

The application follows a layered architecture pattern, which includes:

- **Controller Layer**: This layer handles HTTP requests and responses. It uses services to perform business logic and interact with the database.

- **Service Layer**: This layer contains the business logic of the application. It interacts with repositories to access and manipulate data.

- **Repository Layer**: This layer interacts with the database and provides methods to access and manipulate data.

- **Entity Layer**: This layer represents the tables in the database.

- **Security Layer**: This layer includes classes related to authentication and authorization.

## User Sequence

The user sequence in this application follows these steps:

1. The user sends a request to the server. This could be a request to create, read, update, or delete data.

2. The request is received by the appropriate controller, which then calls the corresponding service method.

3. The service method performs the necessary business logic and interacts with the repository to access or manipulate data.

4. The repository interacts with the database and returns the result to the service.

5. The service returns the result to the controller, which then sends an HTTP response back to the user.

## Controllers

- `AUthController`: Handles authentication-related requests such as registration and login.
- `RendezVousController`: Manages operations related to appointments (RendezVous).
- `CentreController`: Manages operations related to health centers (CentreSante).
- `CreneauController`: Manages operations related to time slots (Creneau).
- `StatsController`: Provides statistical data about the application.
- `UtilisateurController`: Manages operations related to users (Utilisateur).

## Services

- `CentreSanteService`: Handles operations related to health centers.
- `CreneauService`: Handles operations related to time slots.
- `RendezVousService`: Handles operations related to appointments.
- `UtilisateurService`: Handles operations related to users.
- `EmailService`: Handles operations related to sending emails.

## Repositories

- `CentreSanteRepository`
- `CreneauRepository`
- `RendezVousRepository`
- `UtilisateurRepository`
- `RoleRepository`

## Entities

- `CentreSante`
- `Creneau`
- `RendezVous`
- `Utilisateur`
- `Role`

## Security

This includes classes related to authentication and authorization, such as `JwtAuthenticationFilter`, `JwtAuthEntryPoint`, `CustomUserDetailsService`, and `JWTgenerator`.

# API Documentation

This project provides a RESTful API for managing appointments (RendezVous), health centers (CentreSante), time slots (Creneau), and users (Utilisateur). The application also includes authentication and authorization features.

## Endpoints

### Authentication

- `POST /auth/inscreption`: Register a new user.
- `POST /auth/connexion`: Authenticate a user and return a JWT token.

### RendezVous

- `POST /rendezvous`: Create a new appointment.
- `GET /rendezvous/search`: Search for available appointments.
- `GET /rendezvous/{id}`: Get an appointment by its ID.
- `GET /rendezvous`: Get all appointments.
- `PUT /rendezvous/{id}`: Update an appointment.
- `DELETE /rendezvous/{id}`: Delete an appointment.

### CentreSante

- `POST /centres`: Create a new health center.
- `POST /centres/avecCreneau`: Create a new health center with time slots.
- `POST /centres/creneau/{creneauId}`: Create a new health center for a time slot.
- `GET /centres/{id}`: Get a health center by its ID.
- `GET /centres`: Get all health centers.
- `PUT /centres/{id}`: Update a health center.
- `DELETE /centres/{id}`: Delete a health center.

### Creneau

- `POST /creneaux`: Create a new time slot.
- `POST /creneaux/default`: Add default time slots.
- `GET /creneaux/{id}`: Get a time slot by its ID.
- `GET /creneaux`: Get all time slots.
- `PUT /creneaux/{id}`: Update a time slot.
- `DELETE /creneaux/{id}`: Delete a time slot.

### Utilisateur

- `POST /utilisateurs`: Create a new user.
- `GET /utilisateurs`: Get all users.
- `GET /utilisateurs/{id}`: Get a user by its ID.
- `PUT /utilisateurs/{id}`: Update a user.
- `DELETE /utilisateurs/{id}`: Delete a user.

### Stats

- `GET /stats`: Get statistics about the application.

