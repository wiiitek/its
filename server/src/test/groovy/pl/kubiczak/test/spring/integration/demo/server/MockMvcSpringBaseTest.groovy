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

    private static UUID eid01 = UUID.fromString("00000000-0000-0000-a000-00000000001")
    private static UUID eid02 = UUID.fromString("00000000-0000-0000-a000-00000000002")
    private static EmployeeDto employee01 = new EmployeeDto(eid01, "John Doe", "john.doe@example.com")
    private static EmployeeDto employee02 = new EmployeeDto(eid02, "Jane Smith", null)
    private static UUID cid01 = UUID.fromString("00000000-0000-0000-b000-00000000001")
    private static UUID cid02 = UUID.fromString("00000000-0000-0000-b000-00000000002")
    private static CatResponseDto cat01 = new CatResponseDto(cid01, null, "Cats have 9 lives")
    private static CatResponseDto cat02 = new CatResponseDto(cid02, "Thomas", "")


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
        // records response for listing all employees
        employeesService.findAll() >> [employee01, employee02]

        // since we are using web MVC test slice for our Cloud Contract tests
        // we need to record mock responses for some of employee IDs
        employeesService.findByUuid(_ as UUID) >> { UUID uuid ->
            switch (uuid) {
                case eid01 -> Optional.of(employee01)
                case eid02 -> Optional.of(employee02)
                default -> Optional.empty()
            }
        }
    }

    private recordMockAnswersForCats() {
        // records response for listing all cats
        catsService.readAll() >> [cat01, cat02]
    }
}
