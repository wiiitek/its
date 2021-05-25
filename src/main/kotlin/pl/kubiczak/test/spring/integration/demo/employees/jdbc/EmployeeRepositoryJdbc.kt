package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class EmployeeRepositoryJdbc
@Autowired
constructor(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : EmployeeRepository {

    companion object {
        private const val FIND_BY_ID_QUERY = """
            SELECT * FROM employees WHERE uuid = :employeeUuid
        """
    }

    override fun findByUuid(uuid: UUID): Optional<EmployeeEntity> {
        val params = mapOf("employeeUuid" to uuid)
        val result = namedParameterJdbcTemplate.query(FIND_BY_ID_QUERY, params, EmployeeEntityMapper())
        return if (result.isEmpty())
            Optional.empty()
        else
            Optional.of(result[0])
    }
}
