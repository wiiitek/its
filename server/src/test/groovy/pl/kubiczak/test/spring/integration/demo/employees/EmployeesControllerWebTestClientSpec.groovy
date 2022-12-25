package pl.kubiczak.test.spring.integration.demo.employees

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = [
        EmployeesController.class
])
class EmployeesControllerWebTestClientSpec extends Specification {

    @MockBean
    EmployeesService employeesService

    WebTestClient webTestClient

    // https://spring.getdocs.org/en-US/spring-framework-docs/docs/testing/integration-testing/webtestclient.html
    def setup() {
        this.webTestClient = WebTestClient
                .bindToController(new EmployeesController(employeesService))
                .configureClient()
                .build()
    }

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
