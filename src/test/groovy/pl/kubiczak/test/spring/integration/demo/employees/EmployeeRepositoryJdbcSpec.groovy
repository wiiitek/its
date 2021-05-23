package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import spock.lang.Specification

@DataJdbcTest
class EmployeeRepositoryJdbcSpec extends Specification {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryJdbc(namedParameterJdbcTemplate)
    }

    def "should find sample user in database"() {
        when:
        def actual = tested.findById(-1)

        then:
        actual.isPresent()

        and:
        actual.get() == new EmployeeEntity(-1, 'John Doe', 'john.doe@example.com')
    }
}
