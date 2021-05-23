package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.util.*

data class EmployeeEntity(
    val id: Int?,
    val uuid: UUID,
    val name: String,
    val email: String?
)

class EmployeeEntityMapper : RowMapper<EmployeeEntity> {

    override fun mapRow(rs: ResultSet, rowNum: Int): EmployeeEntity? {
        val id = rs.getInt("id")
        val uuidStr = rs.getString("uuid")
        val name = rs.getString("name")
        val email = rs.getString("email")

        val uuid = UUID.fromString(uuidStr)
        return EmployeeEntity(id, uuid, name, email)
    }
}
