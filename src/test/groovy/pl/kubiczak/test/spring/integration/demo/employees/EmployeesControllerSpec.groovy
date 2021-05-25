package pl.kubiczak.test.spring.integration.demo.employees

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = [
        EmployeesController.class
])
class EmployeesControllerSpec extends Specification {

    @Autowired
    WebTestClient webTestClient

    def "get employees + expectBodyList"() {

        expect:
        webTestClient.get()
                .uri('/employees')
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto).isEqualTo([])
    }


    def "get employees + expectBody().json"() {

        expect:
        webTestClient.get()
                .uri('/employees')
                .exchange()
                .expectStatus().isOk()
                .expectBody().json('[]')
    }
}
