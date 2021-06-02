package pl.kubiczak.test.spring.integration.demo.employees.jpa

import org.springframework.beans.factory.annotation.Autowired
import pl.kubiczak.test.spring.integration.demo.TcSpringBaseTest

class TcEmployeeRepositoryJpaSpec extends TcSpringBaseTest {

    @Autowired()
    EmployeeRepository tested

    def "should save and find user in database"() {
        given:
        def uuid = UUID.randomUUID()
        def employee = new EmployeeEntity(null, uuid, 'John Doe', 'john.doe@example.com')
        def saved = tested.save(employee)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual.email == 'john.doe@example.com'
        and:
        actual.id != null
        and:
        actual.id == saved.id
    }

    def "should overwrite existing entity and update instances"() {
        given:
        def uuid = UUID.randomUUID()
        def employee = new EmployeeEntity(null, uuid, 'John Dzźż', null)
        tested.save(employee)

        def fixed = new EmployeeEntity(employee.id, uuid, 'John Doe', null)
        def saved = tested.save(fixed)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        // employee is in transient state and will not be updated
        employee.name == 'John Dzźż'
        and:
        fixed.name == 'John Doe'
        and:
        saved.name == 'John Doe'
        and:
        actual.name == 'John Doe'
    }
}
