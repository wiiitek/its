# its

Integration Testing examples for [Spring Test Slices].

Some links about Test Slices:

- [Spring Test Slices at sudoinit5.com]

## @DataJdbcTest

Provide JDBC template, that can be used to test repositories.

Run tests with no connection (that's default) or [embedded database] (H2, DERBY, HSQLDB).
There is also a possibility to use [embedded PostgreSQL].

> By default, tests annotated with @DataJdbcTest are transactional and roll back at the end of each test.

## @DataJpaTest

Similar to @DataJdbcTest.
In our tests we use [TestEntityManager] because repository methods use [first-level cache].

> By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.

## @Testcontainers

1. Slowest, but use *real* database.
2. Database changes made by tests are not roll back at the end.

[Spring Test Slices]: https://www.baeldung.com/spring-tests#5-using-test-slices
[Spring Test Slices at sudoinit5.com]: https://www.sudoinit5.com/post/spring-test-slices/#testing-just-jpa

[embedded database]: https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot/src/main/java/org/springframework/boot/jdbc/EmbeddedDatabaseConnection.java
[embedded PostgreSQL]: https://stackoverflow.com/a/49011982

[TestEntityManager]: https://zetcode.com/springboot/testentitymanager/
[first-level cache]: https://howtodoinjava.com/hibernate/understanding-hibernate-first-level-cache-with-example/
