package pl.kubiczak.test.spring.integration.demo.employees.jpa

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql
import pl.kubiczak.test.spring.integration.demo.FakeDb
import spock.lang.Specification

@DataJpaTest(excludeAutoConfiguration = [
        AutoConfigureTestDatabase
])
@AutoConfigureEmbeddedDatabase
class EmbeddedEmployeeRepositoryJpaSpec extends Specification {

    @Autowired()
    EmployeeRepository tested

    @Sql(scripts = [FakeDb.DATA_INIT_SQL_SCRIPT])
    def "should find sample user by UUID"() {
        when:
        def actual = tested.findByUuid(UUID.fromString('6fe146ed-367e-4f09-a03a-b8569339c8b2'))

        then:
        actual.isPresent()

        and:
        actual.get().email == 'john.doe@example.com'
    }

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
        employee.name == 'John Doe'
        and:
        fixed.name == 'John Doe'
        and:
        saved.name == 'John Doe'
        and:
        actual.name == 'John Doe'
    }
}
