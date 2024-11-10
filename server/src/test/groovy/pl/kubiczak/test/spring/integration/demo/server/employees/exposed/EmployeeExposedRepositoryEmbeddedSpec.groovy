package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

import javax.sql.DataSource

@DataJdbcTest(excludeAutoConfiguration = [
        AutoConfigureTestDatabase
])
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY
)
class EmployeeExposedRepositoryEmbeddedSpec extends Specification {

    @Autowired
    DataSource dataSource

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryExposed(dataSource)
    }

    def "should create and find user in database (with email: #userEmail)"() {
        given:
        def created = tested.create("Foo", userEmail)

        when:
        def actual = tested.findByUuid(created.uuid)

        then:
        actual != null

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
        exception.message
                .contains('new row for relation "employees" violates check constraint "employees_name_check"')
    }
}
