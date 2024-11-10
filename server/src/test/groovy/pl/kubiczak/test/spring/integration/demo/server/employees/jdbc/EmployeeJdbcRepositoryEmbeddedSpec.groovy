package pl.kubiczak.test.spring.integration.demo.server.employees.jdbc

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import pl.kubiczak.test.spring.integration.demo.server.TestDb
import spock.lang.Specification

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType

@DataJdbcTest(excludeAutoConfiguration = [
        AutoConfigureTestDatabase
])
@AutoConfigureEmbeddedDatabase(
        type = DatabaseType.POSTGRES,
        provider = DatabaseProvider.ZONKY
)
class EmployeeJdbcRepositoryEmbeddedSpec extends Specification {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryJdbc(namedParameterJdbcTemplate)
    }

    @Sql(scripts = [TestDb.DATA_INIT_SQL_SCRIPT])
    def "should find sample user by UUID"() {
        when:
        def actual = tested.findByUuid(UUID.fromString('6fe146ed-367e-4f09-a03a-b8569339c8b2'))

        then:
        actual.isPresent()

        and:
        actual.get().email == 'john.doe@example.com'
    }

    def "should insert and find user in database (with email: #userEmail)"() {
        given:
        def employee = new EmployeeEntity('John Doe', userEmail)
        def uuid = employee.uuid
        tested.insert(employee)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual.email == userEmail
        and:
        actual.id != null

        where:
        userEmail              || _
        'john.doe@example.com' || _
        ''                     || _
        null                   || _
    }

    def "should upsert and find user in database"() {
        given:
        def employee = new EmployeeEntity('John Doe', 'john.doe@example.com')
        def uuid = employee.uuid
        tested.upsert(employee)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual.email == 'john.doe@example.com'
        and:
        actual.id != null
    }

    def "should throw exception for user with empty name"() {
        given:
        def employee = new EmployeeEntity('', 'bar@example.com')

        when:
        tested.upsert(employee)

        then:
        def exception = thrown(DataIntegrityViolationException)
        and:
        exception.message
                .contains('new row for relation "employees" violates check constraint "employees_name_check"')
    }
}
