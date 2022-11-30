package pl.kubiczak.test.spring.integration.demo.employees.exposed

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import pl.kubiczak.test.spring.integration.demo.employees.EmployeeDto
import java.util.*
import javax.sql.DataSource

@Repository
class EmployeeRepositoryExposed(
        @Autowired dataSource: DataSource
) : EmployeeRepository {

    private val database: Database = Database.connect(dataSource)

    override fun create(name: String, email: String?): EmployeeDto {
        TODO("Not yet implemented")
    }

    override fun findByUuid(uuid: UUID): EmployeeDto? {
        TODO("Not yet implemented")
    }

}
