package pl.kubiczak.test.spring.integration.demo

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Testcontainers
@ContextConfiguration(initializers = [TcDbInitializer.class])
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TcSpringBaseTest extends Specification {
}