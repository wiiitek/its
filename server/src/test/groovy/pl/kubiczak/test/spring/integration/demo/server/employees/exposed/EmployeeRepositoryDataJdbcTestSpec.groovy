package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import spock.lang.Specification

import javax.sql.DataSource

@DataJdbcTest
class EmployeeRepositoryDataJdbcTestSpec extends Specification {

    @Autowired
    DataSource dataSource

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryExposed(dataSource)
    }

    def "should create and find user in database"() {
        given:
        def created = tested.create('John Doe', 'john.doe@example.com')

        when:
        def actual = tested.findByUuid(created.uuid)

        then:
        actual.email == 'john.doe@example.com'
        and:
        actual.uuid == created.uuid
    }
}
