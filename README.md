# SENG201 Team019 Project Overview

This project is a JavaFX-based racing game. Players can acquire and customise cars with various upgrades in a garage. They can then participate in races against opponents on different routes, managing fuel and dealing with random events.

## Authors

- [Ethan Elliot](@ethanelliot)
- [Joran Le Quellec](@JoranQuack)
- SENG201 Teaching Team (project structure setup)

## Prerequisites

- JDK >= 21 [click here to get the latest stable OpenJDK release](https://jdk.java.net/21/)
- _(optional)_ Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)

## Run Locally

Clone the project

```bash
  git clone https://eng-git.canterbury.ac.nz/seng201-2025/team-019.git
```

Go to the project directory and run

```bash
  ./gradlew run
```

## Build Project

Create a packaged Jar

```bash
  ./gradlew jar
```

Go to build directory

```bash
  cd build/libs
```

Open the application

```bash
  java -jar seng201_team019-1.0-SNAPSHOT.jar
```

> [!IMPORTANT]
> This Jar is NOT cross-platform, so you must build the jar on the appropriate OS (and machine) to where you want to run it.

## Running Tests

To run tests, run the following command

```bash
  ./gradlew test
```
