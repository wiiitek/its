package pl.kubiczak.test.spring.integration.demo.employees

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@RunWith(SpringRunner.class)
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
