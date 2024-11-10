package pl.kubiczak.test.spring.integration.demo.server.employees.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.jdbc.Sql
import pl.kubiczak.test.spring.integration.demo.server.TestDb
import spock.lang.Specification

@DataJpaTest
class EmployeeJpaRepositoryDataSpec extends Specification {

    @Autowired()
    EmployeeRepository tested

    @Autowired
    TestEntityManager testEntityManager

    @Sql(scripts = [TestDb.DATA_INIT_SQL_SCRIPT])
    def "should find sample user by UUID"() {
        when:
        def actual = tested.findByUuid(UUID.fromString('6fe146ed-367e-4f09-a03a-b8569339c8b2'))

        then:
        actual.isPresent()

        and:
        actual.get().email == 'john.doe@example.com'
    }

    def "should save and find user in database (with email: #userEmail)"() {
        given:
        def employee = new EmployeeEntity('John Doe', userEmail)
        def uuid = employee.uuid
        def saved = testEntityManager.persistFlushFind(employee)

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
        def saved = testEntityManager.persistFlushFind(employee)

        saved.name = 'John Doe'
        testEntityManager.persistFlushFind(saved)

        when:
        def actual = tested.findByUuid(uuid).get()

        then:
        actual.name == 'John Doe'
    }
}
