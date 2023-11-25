package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import pl.kubiczak.test.spring.integration.demo.server.employees.EmployeeDto
import java.util.UUID
import javax.sql.DataSource

@Repository
class EmployeeRepositoryExposed(
    @Autowired dataSource: DataSource
) : EmployeeRepository {

    private val database: Database = Database.connect(dataSource)

    override fun create(name: String, email: String?): EmployeeDto =
        transaction {
            val uuid = UUID.randomUUID()
            EmployeeTable.insert {
                it[EmployeeTable.uuid] = uuid
                it[EmployeeTable.name] = name
                it[EmployeeTable.email] = email
            }
            EmployeeDto(uuid = uuid, name = name, email = email)
        }

    override fun findByUuid(uuid: UUID): EmployeeDto? =
        transaction {
            addLogger(StdOutSqlLogger)

            val found = EmployeeTable
                .select(EmployeeTable.uuid eq uuid)
                .map {
                    EmployeeDto(
                        uuid = it[EmployeeTable.uuid],
                        name = it[EmployeeTable.name],
                        email = it[EmployeeTable.email],
                    )
                }
            check(found.size <= 1) { "Should find at most one row for id: $uuid, but found ${found.size}" }
            found.firstOrNull()
        }
}
