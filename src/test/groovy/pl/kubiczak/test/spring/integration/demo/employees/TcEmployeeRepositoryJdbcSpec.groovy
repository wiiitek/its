package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.beans.factory.annotation.Autowired
import pl.kubiczak.test.spring.integration.demo.TcSpringBaseTest

class TcEmployeeRepositoryJdbcSpec extends TcSpringBaseTest {

    @Autowired
    EmployeeRepository tested

    def "should find sample user in database"() {

        when:
        def actual = tested.findById(-1)

        then:
        actual != Optional.empty()
    }
}
