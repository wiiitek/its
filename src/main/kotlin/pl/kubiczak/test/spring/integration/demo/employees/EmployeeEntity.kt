package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

data class EmployeeEntity(val id: Int?, val name: String, val email: String)

class EmployeeEntityMapper : RowMapper<EmployeeEntity> {

    override fun mapRow(rs: ResultSet, rowNum: Int): EmployeeEntity? {
        val id = rs.getInt("id");
        val name = rs.getString("name")
        val email = rs.getString("email")
        return EmployeeEntity(id, name, email)
    }
}
