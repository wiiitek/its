# its

Integration Testing examples for [Spring Test Slices].

## @DataJdbcTest

Provide JDBC template, that can be used to test repositories.

Run tests with no connection (that's default) or [embedded database] (H2, DERBY, HSQLDB).
There is also a possibility to use [embedded PostgreSQL].

> By default, tests annotated with @DataJdbcTest are transactional and roll back at the end of each test.

## @DataJpaTest

Similar to @DataJdbcTest.

> By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.

## @Testcontainers

1. Slowest, but use *real* database.
2. Database changes made by tests are not roll back at the end.

[Spring Test Slices]: https://www.baeldung.com/spring-tests#5-using-test-slices
[embedded database]: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/jdbc/EmbeddedDatabaseConnection.java
[embedded PostgreSQL]: https://stackoverflow.com/a/49011982
