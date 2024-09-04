
# Warehouse and Central Monitoring Service

This project consists of two Spring Boot applications:
- **Warehouse Service**: Collects data from various sensors and sends it to a central monitoring service.
- **Central Monitoring Service**: Monitors sensor data, checks against configured thresholds, and triggers alarms if thresholds are exceeded.

## Prerequisites

- **Java 21**: Ensure JDK 21 installed.
- **RabbitMQ**: Ensure RabbitMQ is installed and running. Use Docker to quickly set up a RabbitMQ instance (see below).
- **Netcat**: Used for simulating UDP sensor data.

## Running RabbitMQ

Start RabbitMQ using Docker with the following command:

```sh
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

## Steps to Execute Unit Tests
Navigate to the Central Monitoring Service Directory and run tests with gradle as below

```sh
./gradlew test

```

## Building and Running Services

## Warehouse Service
1) Navigate to the Warehouse Service Directory, and build the application

```sh
./gradlew build
```

2) Start the service using:

```sh
./gradlew bootRun
```

## Central Monitoring Service
1) Navigate to the Central Monitoring Service Directory and build the application

```sh
./gradlew build
```

2) Start the service using:

```sh
./gradlew bootRun
```

## Simulating Sensor Data

Simulate sending sensor data using netcat. Ensure netcat is installed on your system.

Simulate Temperature Sensor Data
1. Open a Terminal
2. Send Data

```sh
echo "sensor_id=t1; value=30" | nc -u -w1 localhost 3344
```

## Simulate Humidity Sensor Data

Open a New Terminal
1. Send Data

```sh
echo "sensor_id=h1; value=40" | nc -u -w1 localhost 3355
```

## Verifying the Services

Check Warehouse Service Logs
Verify that the Warehouse Service is receiving UDP messages and processing them. Look for log entries indicating received data.

Check Central Monitoring Service Logs
Ensure that the Central Monitoring Service is receiving messages from RabbitMQ and processing them. Look for alarm messages or log entries indicating that thresholds were exceeded.

Example Alert messages

```
2024-09-04T19:47:59.396+08:00 ERROR 22669 --- [central-monitoring-service] [ntContainer#0-1] com.cms.service.SensorService            : ALARM: Temperature exceeded threshold. Value: 60
2024-09-04T19:48:31.625+08:00 ERROR 22669 --- [central-monitoring-service] [ntContainer#0-1] com.cms.service.SensorService            : ALARM: Humidity exceeded threshold. Value: 74
```


