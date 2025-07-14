# Microservices Application

This is a microservices-based application built with Spring Boot, consisting of multiple services including catalog-service, order-service, notification-service, api-gateway, and webapp.

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Task runner (https://taskfile.dev/)

## Services

![BookStore Microservices Architecture](docs/bookstore-spring-microservices.png)

The application consists of the following services:
- **catalog-service**: Product catalog management
- **order-service**: Order processing
- **notification-service**: Notification handling
- **api-gateway**: API gateway for routing requests
- **webapp**: Web application frontend

## Quick Start

### 1. Clone the Repository

```
git clone git@github.com:Ase020/bookstore-microservice.git
cd bookstore-microservice
```

### 2. Start the Complete Application

To start all services with infrastructure and monitoring:

```bash
task start
```

This command will:
- Format the code
- Build all Docker images
- Start infrastructure services (databases, message queues, etc.)
- Start monitoring services
- Start all application services

### 3. Access the Application

Once started, you can access:
- **Web Application**: Check the webapp service logs for the port
- **API Gateway**: Check the api-gateway service logs for the port

## Available Commands

### Development Commands

```bash
# Run tests (includes code formatting)
task test

# Format code only
task format

# Build all Docker images
task build
```

### Infrastructure Management

```bash
# Start infrastructure services only (databases, etc.)
task start_infra

# Stop infrastructure services
task stop_infra

# Restart infrastructure services
task restart_infra
```

### Monitoring

```bash
# Start monitoring services (Prometheus, Grafana, etc.)
task start_monitoring

# Stop monitoring services
task stop_monitoring

# Restart monitoring services
task restart_monitoring
```

### Application Management

```bash
# Start all services (infrastructure + monitoring + apps)
task start

# Stop all services
task stop

# Restart all services
task restart
```

## Development Workflow

### For Local Development

1. **Start infrastructure services**:
   ```bash
   task start_infra
   ```

2. **Run individual services** from your IDE or command line as needed

3. **Run tests**:
   ```bash
   task test
   ```

### For Full System Testing

1. **Start everything**:
   ```bash
   task start
   ```

2. **Test the application** through the web interface or API

3. **Stop everything**:
   ```bash
   task stop
   ```

## Docker Compose Files

The application uses multiple Docker Compose files:
- `deployment/docker-compose/infra.yml`: Infrastructure services
- `deployment/docker-compose/apps.yml`: Application services
- `deployment/docker-compose/monitoring.yml`: Monitoring services

## Troubleshooting

### Services Not Starting

1. **Check if ports are available**: Make sure required ports are not in use
2. **Verify Docker is running**: Ensure Docker daemon is running
3. **Check logs**: Use `docker compose logs <service-name>` to check individual service logs

### Build Issues

1. **Clean build**: Run `task build` to rebuild all images
2. **Check Java version**: Ensure Java 17+ is installed
3. **Maven wrapper**: The project uses Maven wrapper (`mvnw`/`mvnw.cmd`)

### Memory Issues

If you encounter memory issues:
1. **Increase Docker memory**: Allocate more memory to Docker
2. **Stop unnecessary services**: Use individual commands to start only needed services

## Cross-Platform Support

The Taskfile automatically detects your operating system and uses appropriate commands:
- **Windows**: Uses `mvnw.cmd` and `timeout` command
- **Linux/macOS**: Uses `./mvnw` and `sleep` command

## Contributing

1. Format your code: `task format`
2. Run tests: `task test`
3. Build and test locally: `task start`
4. Submit your changes

## Default Task

Running `task` without any arguments will execute the test suite by default.