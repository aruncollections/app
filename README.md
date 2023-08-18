# Tracker App Backend Service

## Tools 
- Java 18
- Spring Boot 2.7
- Spring Data
- Mysql 5.x 
- Gradle 8.2.1

## Instructions to run
In the root project directory run the any of following commands:

To apply spotless for code cleaning:
```
./gradlew spotlessCheck
./gradlew spotlessApply
```

To build:
```
./gradlew clean build
./gradlew clean bootJar
```

To run:
```
./gradlew clean bootRun
```
