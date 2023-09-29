package pl.kubiczak.test.spring.integration.demo

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import pl.kubiczak.test.spring.integration.demo.employees.EmployeesService
import spock.lang.Specification

@WebMvcTest
// Needed also for spring cloud contract (see build.gradle.kts)
class MockMvcSpringBaseTest extends Specification {

    @MockBean
    EmployeesService employeesService

    @Autowired
    private MockMvc mockMvc

    def setup() {
        // needed for Spring Cloud Contract
        RestAssuredMockMvc.mockMvc(mockMvc)
    }
}
