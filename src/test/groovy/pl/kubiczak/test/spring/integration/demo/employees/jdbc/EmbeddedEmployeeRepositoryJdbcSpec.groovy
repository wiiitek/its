package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification

@DataJdbcTest(excludeAutoConfiguration = [
        AutoConfigureTestDatabase
])
@AutoConfigureEmbeddedDatabase
class EmbeddedEmployeeRepositoryJdbcSpec extends Specification {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryJdbc(namedParameterJdbcTemplate)
    }

    @Sql(scripts = ['/db/scripts/sample_employees.sql'])
    def "should find sample user in database"() {
        when:
        def actual = tested.findByUuid(UUID.fromString('6fe146ed-367e-4f09-a03a-b8569339c8b2'))

        then:
        actual.isPresent()

        and:
        actual.get().email == 'john.doe@example.com'
    }
}
