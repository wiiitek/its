package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.util.*

class EmployeeEntity {

    val id: Long?
    val uuid: UUID
    val name: String
    val email: String?

    constructor(name: String, email: String?) : this(null, UUID.randomUUID(), name, email)

    private constructor(id: Long?, uuid: UUID, name: String, email: String?) {
        this.id = id
        this.uuid = uuid
        this.name = name
        this.email = email
    }

    class Mapper : RowMapper<EmployeeEntity> {
        override fun mapRow(rs: ResultSet, rowNum: Int): EmployeeEntity {
            val id = rs.getLong("id")
            val uuidStr = rs.getString("uuid")
            val name = rs.getString("name")
            val email = rs.getString("email")

            val uuid = UUID.fromString(uuidStr)

            return EmployeeEntity(id, uuid, name, email)
        }
    }
}
