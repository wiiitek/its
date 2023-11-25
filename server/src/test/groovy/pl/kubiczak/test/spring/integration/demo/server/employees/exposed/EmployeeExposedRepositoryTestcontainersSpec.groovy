package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.springframework.beans.factory.annotation.Autowired
import pl.kubiczak.test.spring.integration.demo.server.TestcontainersSpringBaseTest

class EmployeeExposedRepositoryTestcontainersSpec extends TestcontainersSpringBaseTest {

    @Autowired
    EmployeeRepository tested

    def "should create and find user in database"() {
        given:
        def created = tested.create("Foo", "bar@example.com")

        when:
        def actual = tested.findByUuid(created.uuid)

        then:
        actual != null
    }
}
