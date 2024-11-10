# its

[![CI](https://github.com/wiiitek/its/actions/workflows/main.yml/badge.svg)](https://github.com/wiiitek/its/actions/workflows/main.yml)
[![sonarcloud.io](https://sonarcloud.io/api/project_badges/measure?project=wiiitek_its&metric=alert_status)](https://sonarcloud.io/dashboard?id=wiiitek_its)

Integration Testing examples for [Spring Test Slices].

Some links about Test Slices:

- [Spring Test Slices at sudoinit5.com]
- [Integration Testing With @DataJpaTest at baeldung.com]

## @DataJdbcTest

[@DataJdbcTest] applies only the Spring configuration relevant to Data JDBC tests.

Tests run with no DB connection by default:

- [`TestEmployeeRepositoryJdbcSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/jdbc/TestEmployeeRepositoryJdbcSpec.groovy)
- [`EmployeeExposedRepositoryDataSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/exposed/EmployeeExposedRepositoryDataSpec.groovy)

It can also be configured to use [embedded database] (H2, DERBY, HSQLDB).
With some effort it is also a possible to use [embedded PostgreSQL]:

- [`EmployeeJdbcRepositoryEmbeddedSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/jdbc/EmployeeJdbcRepositoryEmbeddedSpec.groovy)
- [`EmployeeExposedRepositoryEmbeddedSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/exposed/EmployeeExposedRepositoryEmbeddedSpec.groovy)

> By default, tests annotated with @DataJdbcTest are transactional and roll back at the end of each test.

## @DataJpaTest

[@DataJpaTest] provides Spring configuration to support JPA repositories and Entities:

- [`EmployeeJpaRepositoryDataSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/jpa/EmployeeJpaRepositoryDataSpec.groovy)
- [`EmployeeJpaRepositoryEmbeddedSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/jpa/EmployeeJpaRepositoryEmbeddedSpec.groovy)

they can also inject [TestEntityManager] to help testing repositories while using [first-level cache].

Additional info for using Kotlin with Spring JPA:

- [Hibernate with Kotlin - powered by Spring Boot at kotlinexpertise.com]

`kotlin("plugin.jpa")` provides no-args constructor for our entities (https://stackoverflow.com/a/41365380).

> By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.

## @Testcontainers

We can use testcontainers to create all of our application beans for integration tests.

1. Slowest, but use *real* database.
2. Requires some setup and config values for DB connection:
   - [`TestcontainersSpringBaseTest`](https://github.com/wiiitek/its/blob/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/TestcontainersSpringBaseTest.groovy)
3. Database changes made by tests are **not** rolled back at the end.

Examples:

- [`EmployeeJdbcRepositoryTestcontainersSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/jdbc/EmployeeJdbcRepositoryTestcontainersSpec.groovy)
- [`EmployeeExposedRepositoryTestcontainersSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/exposed/EmployeeExposedRepositoryTestcontainersSpec.groovy)
- [`EmployeeJpaRepositoryTestcontainersSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/jpa/EmployeeJpaRepositoryTestcontainersSpec.groovy)

## @WebMvcTest

[@WebMvcTest] annotation can be used to test controllers separately:

1. With `MockMvc` - compare also [Guide to Testing Spring Boot Applications With MockMvc]
2. With `WebTestClient` - see [Spring framework docs for WebTestClient]

Usually we use it with `@MockBean` for services used by the tested controller.

Examples are provided in:

- [`EmployeesControllerMockMvcSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/EmployeesControllerMockMvcSpec.groovy)
- [`EmployeesControllerWebTestClientSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/EmployeesControllerWebTestClientSpec.groovy)
- [`CatsControllerMockMvcSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/cats/CatsControllerMockMvcSpec.groovy)

We can use `MockMvc` without `@WebMvcTest` annotation
(compare with [Setup Choices](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework/server-setup-options.html) documentation page).
However, even in standalone mode the servlet context still needs to be created.

- [`EmployeesControllerMockMvcUnitSpec`](https://github.com/wiiitek/its/tree/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/employees/EmployeesControllerMockMvcUnitSpec.groovy)

## WireMock

This project uses [WireMock](https://wiremock.org/docs/overview/) to record expected answers from 3rd party service.
In
[CatFactClientSpec](https://github.com/wiiitek/its/blob/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/server/cats/CatFactClientSpec.groovy)
we:

1. specify how WireMock server should respond
2. verify that our code can correctly interact with specified response

## Spring Cloud Contract

[Spring Cloud Contract] describes behaviour of our API with [Contract DSL].

1. On the producer side:
    * Verifies if the server behaves as described in contract
2. On the consumer side:
    * Creates WireMock stub server to test client code

We can write the contract first, and then implement server and client independently.
See also:

* [Spring Cloud Contract Reference Documentation]
* [Spring Cloud Contract samples]

## Springfox

Swagger UI is available at http://localhost:8080/swagger-ui/index.html.

## Gradle upgrade

```bash
./gradlew wrapper --gradle-version latest
```

[Spring Test Slices]: https://www.baeldung.com/spring-tests#5-using-test-slices
[Spring Test Slices at sudoinit5.com]: https://www.sudoinit5.com/post/spring-test-slices/#testing-just-jpa
[Integration Testing With @DataJpaTest at baeldung.com]: https://www.baeldung.com/spring-boot-testing#integration-testing-with-datajpatest

[@DataJdbcTest]: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.autoconfigured-spring-data-jdbc
[@DataJpaTest]: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.autoconfigured-spring-data-jpa
[@WebMvcTest]: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.spring-mvc-tests

[embedded database]: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/jdbc/EmbeddedDatabaseConnection.java
[embedded PostgreSQL]: https://stackoverflow.com/a/49011982

[Hibernate with Kotlin - powered by Spring Boot at kotlinexpertise.com]: https://kotlinexpertise.com/hibernate-with-kotlin-spring-boot/

[TestEntityManager]: https://zetcode.com/springboot/testentitymanager/
[first-level cache]: https://howtodoinjava.com/hibernate/understanding-hibernate-first-level-cache-with-example/

[Guide to Testing Spring Boot Applications With MockMvc]: https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/
[Spring framework docs for WebTestClient]: https://spring.getdocs.org/en-US/spring-framework-docs/docs/testing/integration-testing/webtestclient.html

[Spring Cloud Contract]: https://spring.io/projects/spring-cloud-contract
[Contract DSL]: https://cloud.spring.io/spring-cloud-contract/reference/html/project-features.html#contract-dsl
[Spring Cloud Contract Reference Documentation]: https://cloud.spring.io/spring-cloud-contract/reference/html/index.html
[Spring Cloud Contract samples]: https://github.com/spring-cloud-samples/spring-cloud-contract-samples
