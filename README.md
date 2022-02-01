# its

[![CI](https://github.com/wiiitek/its/actions/workflows/main.yml/badge.svg)](https://github.com/wiiitek/its/actions/workflows/main.yml)

Integration Testing examples for [Spring Test Slices].

Some links about Test Slices:

- [Spring Test Slices at sudoinit5.com]
- [Integration Testing With @DataJpaTest at baeldung.com]

## @DataJdbcTest

Provides JDBC template, that can be used to test repositories.

Runs tests with no connection (that's default) or [embedded database] (H2, DERBY, HSQLDB).
There is also a possibility to use [embedded PostgreSQL].

> By default, tests annotated with @DataJdbcTest are transactional and roll back at the end of each test.

## @DataJpaTest

First some info about Kotlin with Spring JPA:

- [Hibernate with Kotlin - powered by Spring Boot at kotlinexpertise.com]

Similar to @DataJdbcTest.
In our tests we use [TestEntityManager] because repository methods use [first-level cache].
And `kotlin("plugin.jpa")` provides no-args constructor for our entities (https://stackoverflow.com/a/41365380).

> By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.

## @Testcontainers

1. Slowest, but use *real* database.
2. Database changes made by tests are not rolled back at the end.

## @WebMvcTest

Might be used to test controllers separately:

1. With `MockMvc` - compare also [Guide to Testing Spring Boot Applications With MockMvc]
2. With `WebTestClient` - see [Spring framework docs for WebTestClient]

## Springfox

We use version 3.0.0 with Swagger UI available at http://localhost:8080/swagger-ui/.

[Spring Test Slices]: https://www.baeldung.com/spring-tests#5-using-test-slices
[Spring Test Slices at sudoinit5.com]: https://www.sudoinit5.com/post/spring-test-slices/#testing-just-jpa
[Integration Testing With @DataJpaTest at baeldung.com]: https://www.baeldung.com/spring-boot-testing#integration-testing-with-datajpatest

[embedded database]: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/jdbc/EmbeddedDatabaseConnection.java
[embedded PostgreSQL]: https://stackoverflow.com/a/49011982

[Hibernate with Kotlin - powered by Spring Boot at kotlinexpertise.com]: https://kotlinexpertise.com/hibernate-with-kotlin-spring-boot/

[TestEntityManager]: https://zetcode.com/springboot/testentitymanager/
[first-level cache]: https://howtodoinjava.com/hibernate/understanding-hibernate-first-level-cache-with-example/

[Guide to Testing Spring Boot Applications With MockMvc]: https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/
[Spring framework docs for WebTestClient]: https://spring.getdocs.org/en-US/spring-framework-docs/docs/testing/integration-testing/webtestclient.html
