package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import spock.lang.Specification

import javax.sql.DataSource

@DataJdbcTest(excludeAutoConfiguration = [
        AutoConfigureTestDatabase
])
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.OPENTABLE
)
class EmployeeRepositoryEmbeddedSpec extends Specification {

    @Autowired
    DataSource dataSource

    EmployeeRepository tested

    def setup() {
        tested = new EmployeeRepositoryExposed(dataSource)
    }

    def "should create and find user in database"() {
        given:
        def created = tested.create("Foo", "bar@example.com")

        when:
        def actual = tested.findByUuid(created.uuid)

        then:
        actual != null
    }
}
