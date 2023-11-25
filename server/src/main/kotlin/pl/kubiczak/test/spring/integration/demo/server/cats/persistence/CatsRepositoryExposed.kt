package pl.kubiczak.test.spring.integration.demo.server.cats.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import pl.kubiczak.test.spring.integration.demo.server.cats.CatResponseDto
import java.util.UUID
import javax.sql.DataSource

@Repository
class CatsRepositoryExposed(
    @Autowired dataSource: DataSource
) : CatsRepository {

    private val database: Database = Database.connect(dataSource)

    override fun findAll(): List<CatResponseDto> =
        transaction {
            addLogger(Slf4jSqlDebugLogger)
            CatsTable.selectAll()
                .map {
                    CatResponseDto(
                        id = it[CatsTable.id].value,
                        name = it[CatsTable.name],
                        fact = it[CatsTable.fact]
                    )
                }
        }

    override fun create(name: String?, fact: String): UUID =
        transaction {
            addLogger(Slf4jSqlDebugLogger)

            val uuid = UUID.randomUUID()
            CatsTable.insert {
                it[CatsTable.id] = uuid
                it[CatsTable.name] = name
                it[CatsTable.fact] = fact
            }
            uuid
        }
}
