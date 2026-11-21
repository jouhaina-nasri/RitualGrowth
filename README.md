# Ritual Growth — backend services

[![CI](https://github.com/jouhaina-nasri/RitualGrowth/actions/workflows/ci.yml/badge.svg)](https://github.com/jouhaina-nasri/RitualGrowth/actions/workflows/ci.yml)

Backend for **Ritual Growth**, a product-analytics platform for mobile apps. The apps send screen and session events; the backend stores them as time series and exposes usage data (screen time, devices, cohorts, signup-flow stages, retention and engagement) through REST APIs.

**Stack:** Java 8 · Spring Boot 2.6 · Spring Data JPA · Spring Security (Firebase JWT) · InfluxDB 1.x · Redis · PostgreSQL / H2 · Maven · JUnit 5 · Mockito · Docker · GitHub Actions

## Architecture

```
mobile app ──(screen/session events)──▶ events-influxdb ──▶ InfluxDB  (time series: screen time, devices)
                                              │        └──▶ Redis     (project → cohort → user → pages index)
                                              └──────────▶ PostgreSQL (projects, cohorts, stages, users)

dashboard ─────────────────────────────▶ events-rest-rg ──▶ H2 / InfluxDB (companies, users, access requests)

Authentication: Firebase ID tokens (Google, Apple, Facebook) verified by both services.
```

| Module | Role |
|---|---|
| `events-common` | Shared helpers, request/response models, null-safe utilities |
| `events-data` | JPA entities and InfluxDB measurements (screen events, devices, cohorts, stages, D7/D30, engagement, retention) |
| `events-repos/events-repos-jpa` | JPA and InfluxDB repositories |
| `events-web-common` | Shared controllers, DTOs, search requests, security model |
| `events-influxdb` 💻 | REST service for event tracking — port **8084**, base path `/events` |
| `events-rest-rg` 💻 | Main Ritual Growth REST service — port **8085** |

### Main endpoints (`events-influxdb`)

| Method | Path | Purpose |
|---|---|---|
| `POST` | `/events/screens` | Record a screen event (start/end time, total screen time, session, device) |
| `GET` | `/events/screens` | Query screen events |
| `GET` | `/events/devices` | Devices seen for a user |
| `GET` | `/events/redis/projects`, `/cohorts`, `/users`, `/pages` | Project → cohort → user → page index |
| `GET/POST/DELETE` | `/events/cohort`, `/events/stage`, `/events/signup`, `/events/projects` | Cohorts, funnel stages and projects |

Full API docs (Swagger UI) are served at `/docs` when a service is running: <http://localhost:8084/events/docs> and <http://localhost:8085/docs>.

## Getting started

Requirements: JDK 8, Maven 3.6+, Docker (for the databases).

```bash
mvn -B verify            # build all modules and run the unit tests
```

### Run locally

1. Start the databases:
   ```bash
   docker compose up -d postgres redis influxdb
   ```
2. Put a Firebase Admin SDK service-account key at `./secrets/firebase-adminsdk.json` (the `secrets/` folder is git-ignored), or point `FIREBASE_CONFIG_FILE` to it.
3. Run a service with the default `development` profile:
   ```bash
   SPRING_DATASOURCE_USERNAME=postgres SPRING_DATASOURCE_PASSWORD=postgres \
     mvn -pl events-influxdb -am spring-boot:run
   ```

### Run everything with Docker

```bash
mvn -B package -DskipTests
docker compose up --build
```

## Configuration

Each service has one `.properties` file per Spring profile in `src/main/resources`:

| Profile | Use |
|---|---|
| `development` (default) | Local machine, databases from `docker-compose.yml` |
| `testserver` | Test server deployment |
| `testserver-pt` | Performance-testing instance on the test server |

Choose a profile with `-Dspring.profiles.active=<profile>`. No secrets are stored in the repository; they come from environment variables:

| Variable | Used for |
|---|---|
| `FIREBASE_CONFIG_FILE` | Path to the Firebase Admin SDK key (default `./secrets/firebase-adminsdk.json`) |
| `INFLUX_URL`, `INFLUX_PASSWORD` | InfluxDB on the test server |
| `DATA_DIR` | H2 database, static files and images (default `./data`) |
| `SPRING_DATASOURCE_*`, `SPRING_REDIS_HOST` | Standard Spring Boot overrides |

## Tests

Tests use JUnit 5, Mockito and AssertJ, and run on every push with GitHub Actions.

- **Unit tests** need no external services and run with `mvn test`. They cover the screen-event mapping and screen-time fields, the InfluxDB write format (tags vs fields), the event service (hashing of project/cohort/user ids, validation and error handling), the Redis index service, ID hashing and the shared null-safe utilities.
- **Integration tests** (`@Tag("integration")`, `@SpringBootTest`) need PostgreSQL, Redis and InfluxDB. CI runs them against real service containers; locally they are skipped by default:
  ```bash
  docker compose up -d postgres redis influxdb
  mvn verify -Pintegration-tests
  ```
