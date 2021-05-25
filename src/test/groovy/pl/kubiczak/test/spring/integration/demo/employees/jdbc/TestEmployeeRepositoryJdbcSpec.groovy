package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import pl.kubiczak.test.spring.integration.demo.FakeDb
import spock.lang.Specification

@DataJdbcTest
class TestEmployeeRepositoryJdbcSpec extends Specification {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryJdbc(namedParameterJdbcTemplate)
    }

    @Sql(scripts = [FakeDb.DATA_INIT_SQL_SCRIPT])
    def "should find sample user by UUID"() {
        when:
        def actual = tested.findByUuid(UUID.fromString('6fe146ed-367e-4f09-a03a-b8569339c8b2'))

        then:
        actual.isPresent()

        and:
        actual.get().email == 'john.doe@example.com'
    }
}
