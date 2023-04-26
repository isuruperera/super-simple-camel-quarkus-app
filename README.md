# super-simple-camel-quarkus-app

## Installation

### Prerequisites

Following tools must be installed in the system in order to build the project.

* [OpenJDK 11](https://openjdk.org/projects/jdk/11/)
* [Apache Maven 3.8.1](https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.8.1/)
* [Docker](https://docs.docker.com/)

### Build the project using IDE

In order to build the project using IntelliJ IDEA, go to `Maven > SuperSimpleCamelQuarkusApp > Lifecycle > install` and
double-click on `install` in order to start the build.

### Build the project using CLI

From the project root directory, run the following commands.

```shell
cd src
mvn clean install
```

### Testing the project

All the unit tests and integration tests are written using [JUnit 5](https://junit.org/junit5/). Tests are available
in `src/test` directory. Unit tests ate suffixed with **Test* and the integration tests are
suffixed with **IT.*

#### Running unit tests

From the project root directory, run the following commands.

```shell
cd src
mvn clean test
```

#### Running integration tests

From the project root directory, run the following commands.

```shell
cd src
mvn clean verify
```
