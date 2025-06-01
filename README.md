![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4-green)
![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-green)
![Project Reactor](https://img.shields.io/badge/Project%20Reactor-green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4.1-blue)

# WebFlux Playlists

## 📖 Description

This project implements a reactive backend. The main objective is to create an API that manages a web site about sharing songs and playlists, the project uses Project Reactor and WebFlux to make the service async.

## 🚀 **Technologies**

The main technologies used in this project are:

- ☕ Java 21 
- 🌱 Spring Boot
- 🔄 WebFlux
- ⚛️ Project Reactor
- 🍃 MongoDB
- 🛡️ Spring Security
- 🔐 JWT - HMAC

## 🎯 **Features**
- 👤 Users  
- 🔑 Authentication
- 🎵 Songs  
- 🎧 Playlists

## ⚙ Prerequisites

Install these programs:

- **Java 21**
- **IDE** (IntelliJ IDEA, Eclipse, VSCode.)
- **Postman** (or similar.)

## ⚡ Steps to Run the Project

### 1. Clone the repository

Clone the project to your local environment:

```bash
git clone https://github.com/Dionclei-Pereira/webflux.git
```
### 2. Configure MongoDB

Locate the configuration file, the file is located at:

```text
   src/main/resources/application.properties
```

You can change the URI

example:
```text
  spring.data.mongodb.uri=mongodb://localhost:27017/webflux
```

### 3. Run the Project

To run the project, you can use your IDE or Maven CLI

### 4. Testing the API

The API is configured to allow login and generate a JWT token. You can use **Postman** to test the routes.

- **POST** `/auth/login`: Send an `email` and `password` to receive a JWT token.
- **GET** `/songs`: This route is protected and requires a valid JWT token in the Authorization header.

Example request for login:

POST /auth/login
```json
{
  "email": "email@gmail.com",
  "password": "password"
}
```

If the login is successful, a JWT token will be returned.

Example request to access a protected route:

- **GET** `/songs` <br>
Authorization: _your-jwt-token_

## 📑 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| `POST` | `/auth/login` | Authenticates a user and returns a JWT token | `{ "email": "email@gmail", "password": "passw" }` | `{ "token": "eyJhbGciOiJIUzUxMiJ9..." }` |
| `POST` | `/auth/register` | Authenticates a new user | `{"email": "email@gmail.com","phone": 999999999,"name": "username","password": "password"}` | `200 OK`<br>`400 Bad Request` |
| `GET`  | `/playlists` | Retrieves all playlists | - | `200 OK` |
| `GET`  | `/playlists/{id}` | Retrieves a specific playlist by ID | - | `200 OK`<br>`404 Not Found`|
| `POST`  | `/playlists` | Creates a new playlist | `{"name": "PlaylistName","genres": ["ROCK"]}` | `200 OK`<br>`400 Bad Request`|
| `GET`  | `/playlists/{id}/songs` | Retrieves a specific playlist's list of song by ID| - | `200 OK`<br>`404 Not Found` |
| `PUT`  | `/playlists/{id}/songs/{songId}` | Adds a song to a playlist (You can only add to a playlist that you own)| - | `200 OK`<br>`404 Not Found`<br>`403 Forbidden`|
| `GET`  | `/songs` | Retrieves all songs | - | `200 OK` |
| `GET`  | `/songs/{id}` | Retrieves a specific song by ID | - | `200 OK`<br>`404 Not Found`|
| `POST`  | `/songs` | Creates a new song (You can only create a song in a playlist that you own)| `{"name": "Shape of You", "playlistId": "12345", "link": "https://example.com/song/shape-of-you", "genres": ["POP"]}` | `200 OK`<br>`400 Bad Request`|
| `GET`  | `/users` | Retrieves all users | - | `200 OK` |
| `GET`  | `/users/{id}` | Retrieves a specific user by ID| - | `200 OK`<br>`404 Not Found` |
| `GET`  | `/users/{id}/songs` | Retrieves a specific user's list of favorite songs by ID| - | `200 OK`<br>`404 Not Found` |
| `POST`  | `/users/{id}/songs/{songId}` | Adds a song to favorites (You can only add to a playlist that you own)| - | `200 OK`<br>`404 Not Found`<br>`403 Forbidden`| |
| `DELETE`  | `/users/{id}/songs/{songId}` | Removes a song to favorites (You can only remove to a playlist that you own)| - | `200 OK`<br>`404 Not Found`<br>`403 Forbidden`| |


## 📜Author

**Dionclei de Souza Pereira**

[Linkedin](https://www.linkedin.com/in/dionclei-de-souza-pereira-07287726b/)

⭐️ If you like this project, give it a star!  
