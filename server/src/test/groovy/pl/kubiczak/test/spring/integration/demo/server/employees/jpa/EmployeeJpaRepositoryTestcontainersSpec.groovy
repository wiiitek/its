package pl.kubiczak.test.spring.integration.demo.server.employees.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import pl.kubiczak.test.spring.integration.demo.server.TestcontainersSpringBaseTest

class EmployeeJpaRepositoryTestcontainersSpec extends TestcontainersSpringBaseTest {

    @Autowired()
    EmployeeRepository tested

    def "should save and find user in database (with email: #userEmail)"() {
        given:
        def employee = new EmployeeEntity('John Doe', userEmail)
        def uuid = employee.uuid
        def saved = tested.save(employee)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual == saved
        and:
        actual.id != null

        where:
        userEmail              || _
        'john.doe@example.com' || _
        ''                     || _
        null                   || _
    }

    def "should overwrite existing entity and update row"() {
        given:
        def employee = new EmployeeEntity('John Dzźż', null)
        def uuid = employee.uuid
        def saved = tested.save(employee)

        saved.name = 'John Doe'
        tested.save(saved)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual.name == 'John Doe'
    }

    def "should throw exception for user with empty name"() {
        given:
        def employee = new EmployeeEntity('', 'bar@example.com')

        when:
        tested.save(employee)

        then:
        def exception = thrown(DataIntegrityViolationException)
        and:
        exception.message
                .contains('new row for relation "employees" violates check constraint "employees_name_check"')
    }
}
