# Copilot Instructions for AI Coding Agents

## Project Overview
This repository is a multi-module Java microservices system, managed with Maven and Docker Compose. It includes at least three main services:
- **config-server**: Centralized Spring Cloud Config server for externalized configuration.
- **eureka-server**: Service discovery using Netflix Eureka.
- **micro-company**: Example business microservice (Spring Boot).

## Architecture & Data Flow
- All services are Spring Boot applications, using Maven for builds.
- Services communicate via REST and register with Eureka for discovery.
- Configuration is externalized in `infra/config-server/config-repo/` and loaded at runtime.
- Docker Compose orchestrates multi-service local development.

## Key Workflows
- **Build all services**: Run `mvnw clean package` in each service directory (e.g., `infra/config-server/`).
- **Run locally**: Use Docker Compose (`docker-compose up --build`) from the repo root to start all services with correct dependencies.
- **Test**: Run `mvnw test` in each service directory. Test reports are in `target/surefire-reports/`.
- **Debug**: Standard Spring Boot debugging applies. Use IDE remote debug configs if needed.

## Project Conventions
- **Config files**: Centralized in `infra/config-server/config-repo/`. Service-specific configs use the pattern `<service-name>.yml`.
- **Docker**: Each service has its own `dockerfile` for containerization.
- **No monorepo root build**: Each service is built/tested independently.
- **Java package structure**: Follows `com.ocoelhogabriel.<service>` convention.

## Integration & Dependencies
- **Spring Cloud**: For config and service discovery.
- **Docker Compose**: For local orchestration.
- **Maven**: For dependency management and builds.

## Examples
- To add a new service, replicate the structure of `micro-company` and register it with Eureka.
- To update configuration, edit files in `infra/config-server/config-repo/` and restart the config server.

## References
- `docker-compose.yml`: Service orchestration
- `infra/config-server/config-repo/`: Central config
- `infra/eureka-server/`: Service discovery
- `micro-company/`: Example business service

---

**For AI agents:**
- Prefer explicit service boundaries and RESTful communication.
- Always update config in the config repo, not in service jars.
- Use Maven wrapper scripts (`mvnw`, `mvnw.cmd`) for builds/tests.
- Reference this file for project-specific patterns and workflows.
