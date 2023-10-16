package pl.kubiczak.test.spring.integration.demo.server.employees

import org.springframework.test.web.reactive.server.WebTestClient
import pl.kubiczak.test.spring.integration.demo.server.MockMvcSpringBaseTest

class EmployeesControllerWebTestClientSpec extends MockMvcSpringBaseTest {

    WebTestClient webTestClient

    // https://spring.getdocs.org/en-US/spring-framework-docs/docs/testing/integration-testing/webtestclient.html
    def setup() {
        this.webTestClient = WebTestClient
                .bindToController(new EmployeesController(employeesService))
                .configureClient()
                .build()
    }

    def "get employees + expectBodyList + contains"() {

        expect:
        webTestClient.get()
                .uri('/employees')
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto)
                .contains(
                        new EmployeeDto(
                                UUID.fromString("00000000-0000-0000-a000-000000000001"),
                                "John Doe",
                                "john.doe@example.com"
                        )
                )
                .contains(
                        new EmployeeDto(
                                UUID.fromString("00000000-0000-0000-a000-000000000002"),
                                "Jane Smith",
                                null
                        )
                )
    }


    def "get employees + expectBody().jsonPath"() {

        expect:
        webTestClient.get()
                .uri('/employees')
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("\$[0].uuid").isEqualTo("00000000-0000-0000-a000-000000000001")
                .jsonPath("\$[1].uuid").isEqualTo("00000000-0000-0000-a000-000000000002")
                .jsonPath("\$[0].name").isEqualTo("John Doe")
                .jsonPath("\$[1].name").isEqualTo("Jane Smith")
                .jsonPath("\$[0].email").isEqualTo("john.doe@example.com")
                .jsonPath("\$[1].email").doesNotExist()
    }
}
