package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import spock.lang.Specification

import javax.sql.DataSource

@DataJdbcTest
class EmployeeExposedRepositoryDataSpec extends Specification {

    @Autowired
    DataSource dataSource

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryExposed(dataSource)
    }

    def "should create and find user in database (with email: #userEmail)"() {
        given:
        def created = tested.create('John Doe', userEmail)

        when:
        def actual = tested.findByUuid(created.uuid)

        then:
        actual.email == userEmail
        and:
        actual.uuid == created.uuid

        where:
        userEmail              || _
        'john.doe@example.com' || _
        ''                     || _
        null                   || _
    }

    def "should throw exception for user with empty name"() {
        when:
        tested.create("", "bar@example.com")

        then:
        def exception = thrown(ExposedSQLException)
        and:
        // here we have exception message that comes from H2 database
        exception.message
                .contains('Check constraint violation')
    }
}
