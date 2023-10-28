# Tracker App Backend Service

## Prerequisites

## Tools 
- Java 18
- Spring Boot 2.7
- Spring Data
- Mysql 5.x 
- Gradle 8.2.1

For Mac OS:
- Use `brew` to install the `Node JS`:


Once the Node JS is installed, it should automatically install `npm` and `yarn`.
If not, install them using brew and verify the following versions.

```shell
$ node -v
v20.5.1

$ npm -v
9.8.0

$ yarn -v
1.22.19

```

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
