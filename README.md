# its

[![CI](https://github.com/wiiitek/its/actions/workflows/main.yml/badge.svg)](https://github.com/wiiitek/its/actions/workflows/main.yml)

Integration Testing examples for [Spring Test Slices].

Some links about Test Slices:

- [Spring Test Slices at sudoinit5.com]
- [Integration Testing With @DataJpaTest at baeldung.com]

## @DataJdbcTest

[@DataJdbcTest] applies only the Spring configuration relevant to Data JDBC tests.

Tests run with no DB connection by default, but it can be configured to use [embedded database] (H2, DERBY, HSQLDB).
With some effort it is also a possible to use [embedded PostgreSQL].

> By default, tests annotated with @DataJdbcTest are transactional and roll back at the end of each test.

## @DataJpaTest

[@DataJpaTest] provides Spring configuration to support JPA repositories and Entities.
they can also inject [TestEntityManager] to help testing repositories while using [first-level cache].

Additional info for using Kotlin with Spring JPA:

- [Hibernate with Kotlin - powered by Spring Boot at kotlinexpertise.com]

`kotlin("plugin.jpa")` provides no-args constructor for our entities (https://stackoverflow.com/a/41365380).

> By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.

## @Testcontainers

1. Slowest, but use *real* database.
2. Requires some setup and config values for DB connection
3. Database changes made by tests are not rolled back at the end.

## @WebMvcTest

Might be used to test controllers separately:

1. With `MockMvc` - compare also [Guide to Testing Spring Boot Applications With MockMvc]
2. With `WebTestClient` - see [Spring framework docs for WebTestClient]

## @AutoConfigureWireMock

Simple example for
[@AutoConfigureWireMock](https://cloud.spring.io/spring-cloud-contract/1.2.x/multi/multi__spring_cloud_contract_wiremock.html)
is provided in
[CatFactClientSpec](https://github.com/wiiitek/its/blob/main/server/src/test/groovy/pl/kubiczak/test/spring/integration/demo/cats/CatFactClientSpec.groovy).

## Spring Cloud Contract

This Project currently shows how to record the sample request/response contract.
Some interesting links:

1. [Spring Cloud Contract Getting Started]
2. [Spring Cloud Contract for Gradle project]
3. [Spring Cloud Contract samples]
4. [Ensuring Client and Server are in sync]

## Springfox

Swagger UI is available at http://localhost:8080/swagger-ui/index.html.

[Spring Test Slices]: https://www.baeldung.com/spring-tests#5-using-test-slices
[Spring Test Slices at sudoinit5.com]: https://www.sudoinit5.com/post/spring-test-slices/#testing-just-jpa
[Integration Testing With @DataJpaTest at baeldung.com]: https://www.baeldung.com/spring-boot-testing#integration-testing-with-datajpatest

[@DataJdbcTest]: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.autoconfigured-spring-data-jdbc
[@DataJpaTest]: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.autoconfigured-spring-data-jpa

[embedded database]: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/jdbc/EmbeddedDatabaseConnection.java
[embedded PostgreSQL]: https://stackoverflow.com/a/49011982

[Hibernate with Kotlin - powered by Spring Boot at kotlinexpertise.com]: https://kotlinexpertise.com/hibernate-with-kotlin-spring-boot/

[TestEntityManager]: https://zetcode.com/springboot/testentitymanager/
[first-level cache]: https://howtodoinjava.com/hibernate/understanding-hibernate-first-level-cache-with-example/

[Guide to Testing Spring Boot Applications With MockMvc]: https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/
[Spring framework docs for WebTestClient]: https://spring.getdocs.org/en-US/spring-framework-docs/docs/testing/integration-testing/webtestclient.html

[Spring Cloud Contract Getting Started]: https://cloud.spring.io/spring-cloud-contract/reference/html/getting-started.html
[Spring Cloud Contract for Gradle project]: https://cloud.spring.io/spring-cloud-contract/reference/html/gradle-project.html
[Spring Cloud Contract samples]: https://github.com/spring-cloud-samples/spring-cloud-contract-samples
[Ensuring Client and Server are in sync]: https://www.linkedin.com/learning/advanced-spring-effective-integration-testing-with-spring-boot/ensuring-client-app-rest-call-and-web-app-controller-are-in-sync-10134626
