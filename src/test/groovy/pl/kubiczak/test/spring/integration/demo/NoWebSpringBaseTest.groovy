package pl.kubiczak.test.spring.integration.demo

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class NoWebSpringBaseTest extends Specification {
}
