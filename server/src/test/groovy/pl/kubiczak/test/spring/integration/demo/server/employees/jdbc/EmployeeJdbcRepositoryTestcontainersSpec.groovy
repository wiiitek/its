package pl.kubiczak.test.spring.integration.demo.server.employees.jdbc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import pl.kubiczak.test.spring.integration.demo.server.TestcontainersSpringBaseTest

class EmployeeJdbcRepositoryTestcontainersSpec extends TestcontainersSpringBaseTest {

    @Autowired
    EmployeeRepository tested

    def "should save and find user in database"() {
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
