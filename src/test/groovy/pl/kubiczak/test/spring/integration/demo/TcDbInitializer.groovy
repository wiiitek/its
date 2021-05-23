package pl.kubiczak.test.spring.integration.demo


import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

class TcDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer<>("postgres:12.3")

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
