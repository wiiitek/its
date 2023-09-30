package pl.kubiczak.test.spring.integration.demo.employees


import org.springframework.test.web.reactive.server.WebTestClient
import pl.kubiczak.test.spring.integration.demo.MockMvcSpringBaseTest

class EmployeesControllerWebTestClientSpec extends MockMvcSpringBaseTest {

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
