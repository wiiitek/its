package pl.kubiczak.test.spring.integration.demo.server

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import pl.kubiczak.test.spring.integration.demo.server.employees.EmployeeDto
import pl.kubiczak.test.spring.integration.demo.server.employees.EmployeesService
import spock.lang.Specification

@WebMvcTest
// Needed also for spring cloud contract (see build.gradle.kts)
class MockMvcSpringBaseTest extends Specification {

    private static UUID id01 = UUID.fromString("00000000-0000-0000-a000-00000000001")
    private static UUID id02 = UUID.fromString("00000000-0000-0000-a000-00000000002")
    private static EmployeeDto sample01 = new EmployeeDto(id01, "John Doe", "john.doe@example.com")
    private static EmployeeDto sample02 = new EmployeeDto(id02, "Jane Smith", null)


    // https://spockframework.org/spock/docs/1.2/module_spring.html#_using_code_springbean_code
    @SpringBean
    EmployeesService employeesService = Stub()

    @Autowired
    private MockMvc mockMvc

    def setup() {
        // needed for Spring Cloud Contract
        RestAssuredMockMvc.mockMvc(mockMvc)

        // records response for listing all employees
        employeesService.findAll() >> [sample01, sample02]
        // since we are using web MVC test slice for our Cloud Contract tests
        // we need to record mock responses for some of employee IDs
        Map<UUID, EmployeeDto> mockEmployees = [
                (id01 as UUID): sample01,
                (id02 as UUID): sample02,
        ]
        employeesService.findByUuid(_ as UUID) >> { UUID uuid ->
            def mockEmployee = mockEmployees[uuid]
            Optional.ofNullable(mockEmployee)
        }
    }
}
