package pl.kubiczak.test.spring.integration.demo.server

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Testcontainers
@ContextConfiguration(initializers = [
        TestcontainersDbInitializer.class
])
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TestcontainersSpringBaseTest extends Specification {

    @LocalServerPort
    protected int localServerPort

    static class TestcontainersDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Container
        public static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer<>("postgres:14.12")

        @Override
        void initialize(ConfigurableApplicationContext context) {
            CONTAINER.start()
            TestPropertyValues
                    .of(
                            "spring.datasource.url=" + CONTAINER.getJdbcUrl(),
                            "spring.datasource.username=" + CONTAINER.getUsername(),
                            "spring.datasource.password=" + CONTAINER.getPassword()
                    )
                    .applyTo(context.getEnvironment())
        }
    }
}
