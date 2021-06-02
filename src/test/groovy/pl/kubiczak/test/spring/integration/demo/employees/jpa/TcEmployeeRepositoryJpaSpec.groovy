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
        def saved = tested.save(employee)

        def fixed = new EmployeeEntity(employee.id, uuid, 'John Doe', null)
        def savedFixed = tested.save(fixed)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        // why JPA not recognize those two entities as same?
        // is it because we lack equals + hashcode method in entity?
        // is it because we are using *real* database?
        saved.name == 'John Dzźż'
        and:
        savedFixed.name == 'John Doe'
        and:
        actual.name == 'John Doe'
        and:
        actual.name == 'John Doe'
        and:
        actual.name == 'John Doe'
    }
}
