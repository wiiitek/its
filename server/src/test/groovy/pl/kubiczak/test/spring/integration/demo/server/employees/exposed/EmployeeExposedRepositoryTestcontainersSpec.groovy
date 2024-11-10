package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.springframework.beans.factory.annotation.Autowired
import pl.kubiczak.test.spring.integration.demo.server.TestcontainersSpringBaseTest

class EmployeeExposedRepositoryTestcontainersSpec extends TestcontainersSpringBaseTest {

    @Autowired
    EmployeeRepository tested

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
