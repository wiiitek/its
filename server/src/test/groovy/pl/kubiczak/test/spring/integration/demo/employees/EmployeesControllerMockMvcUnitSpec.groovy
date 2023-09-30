package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class EmployeesControllerMockMvcUnitSpec extends Specification {

    private EmployeesService employeesService

    private EmployeesController employeesController

    private MockMvc mockMvc

    // https://spring.getdocs.org/en-US/spring-framework-docs/docs/testing/integration-testing/webtestclient.html
    def setup() {
        employeesService = Mock()
        employeesController = new EmployeesController(employeesService)

        mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build()
    }

    private def recordMockWithOneSampleEmployee(String id, String name, String email = null) {
        def uuid = UUID.fromString(id)
        employeesService.findAll() >> [
                new EmployeeDto(uuid, name, email)
        ]
    }

    def "should return empty list (compare to string)"() {
        given:
        employeesService.findAll() >> []

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(content().string('[]'))
    }

    def "should return JSON with employee with correct ID"() {
        given:
        def nil = "00000000-0000-0000-0000-000000000000"
        recordMockWithOneSampleEmployee(nil, "John")

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Type"))
                .andExpect(header().stringValues("Content-Type", "application/json"))
                .andExpect(jsonPath('[0]').exists())
                .andExpect(jsonPath('[0].uuid').exists())
                .andExpect(jsonPath('[0].uuid').isString())
                .andExpect(jsonPath('[0].uuid').value(nil))
    }

    def "should return JSON with employee with null email"() {
        given:
        def randomId = UUID.randomUUID().toString()
        recordMockWithOneSampleEmployee(randomId, "John")

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Type"))
                .andExpect(header().stringValues("Content-Type", "application/json"))
                .andExpect(jsonPath('[0]').exists())
                .andExpect(jsonPath('[0].email').doesNotExist())
    }
}
