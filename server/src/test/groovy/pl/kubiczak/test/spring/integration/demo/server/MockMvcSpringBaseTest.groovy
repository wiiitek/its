package pl.kubiczak.test.spring.integration.demo.server

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import pl.kubiczak.test.spring.integration.demo.server.cats.CatResponseDto
import pl.kubiczak.test.spring.integration.demo.server.cats.CatsService
import pl.kubiczak.test.spring.integration.demo.server.employees.EmployeeDto
import pl.kubiczak.test.spring.integration.demo.server.employees.EmployeesService
import spock.lang.Specification

@WebMvcTest
// Needed also for spring cloud contract (see build.gradle.kts)
class MockMvcSpringBaseTest extends Specification {

    // https://spockframework.org/spock/docs/1.2/module_spring.html#_using_code_springbean_code
    @SpringBean
    EmployeesService employeesService = Stub()

    @SpringBean
    CatsService catsService = Stub()

    @Autowired
    private MockMvc mockMvc

    def setup() {
        // needed for Spring Cloud Contract
        RestAssuredMockMvc.mockMvc(mockMvc)

        recordMockAnswersForEmployees()
        recordMockAnswersForCats()
    }

    private recordMockAnswersForEmployees() {
        UUID eid01 = UUID.fromString("00000000-0000-0000-a000-00000000001")
        UUID eid02 = UUID.fromString("00000000-0000-0000-a000-00000000002")
        def employee01 = new EmployeeDto(eid01, "John Doe", "john.doe@example.com")
        def employee02 = new EmployeeDto(eid02, "Jane Smith", null)
        // records response for listing all employees
        employeesService.findAll() >> [employee01, employee02]

        // since we are using web MVC test slice for our Cloud Contract tests
        // we need to record mock responses for some of employee IDs
        def employeesMap = [
                (eid01): employee01,
                (eid02): employee02,
        ]
        employeesService.findByUuid(_ as UUID) >> { UUID uuid ->
            def employee = employeesMap[uuid]
            Optional.ofNullable(employee)
        }
    }

    private recordMockAnswersForCats() {
        UUID cid01 = UUID.fromString("00000000-0000-0000-b000-00000000001")
        UUID cid02 = UUID.fromString("00000000-0000-0000-b000-00000000002")
        def cat01 = new CatResponseDto(cid01, null, "Cats have 9 lives")
        def cat02 = new CatResponseDto(cid02, "Thomas", "")

        // records response for listing all cats
        catsService.readAll() >> [cat01, cat02]
    }
}
