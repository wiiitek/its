package pl.kubiczak.test.spring.integration.demo.employees.exposed

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.jdbc.Sql
import pl.kubiczak.test.spring.integration.demo.TestDb
import spock.lang.Specification

import javax.sql.DataSource

@DataJdbcTest(excludeAutoConfiguration = [
        AutoConfigureTestDatabase
])
@AutoConfigureEmbeddedDatabase(
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.OPENTABLE
)
class EmbeddedEmployeeRepositoryExposedSpec extends Specification {

    @Autowired
    DataSource dataSource

    EmployeeRepository tested

    private UUID employeeId = UUID.fromString('6fe146ed-367e-4f09-a03a-b8569339c8b2')

    def setup() {
        tested = new EmployeeRepositoryExposed(dataSource)
    }

    @Sql(scripts = [TestDb.DATA_INIT_SQL_SCRIPT])
    def "should find sample user by UUID"() {
        when:
        def actual = tested.findByUuid(employeeId)

        then:
        actual != null
    }
}
