package pl.kubiczak.test.spring.integration.demo.server.employees.jdbc

import org.springframework.beans.factory.annotation.Autowired
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

    def "should insert and find user in database"() {
        given:
        def employee = new EmployeeEntity('John Doe', 'john.doe@example.com')
        def uuid = employee.uuid
        tested.insert(employee)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual.email == 'john.doe@example.com'
        and:
        actual.id != null
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
}
