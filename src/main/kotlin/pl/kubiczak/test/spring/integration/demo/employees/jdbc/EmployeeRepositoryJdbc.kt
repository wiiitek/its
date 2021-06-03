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
        private const val FIRST = 0
        private const val INSERT = """
            INSERT INTO employees (id, uuid, name, email)
            VALUES(nextval('employees_seq'), :uuid_param, :name_param, :email_param) 
        """
        private const val UPSERT = """
            INSERT INTO employees (id, uuid, name, email)
            VALUES(nextval('employees_seq'), :uuid_param, :name_param, :email_param) 
            ON CONFLICT (uuid) 
                DO UPDATE
                SET name = EXCLUDED.name, email = EXCLUDED.email WHERE employees.uuid = :uuid_param;
        """
        private const val FIND_BY_ID = """
            SELECT *
            FROM employees
            WHERE uuid = :employeeUuid;
        """
    }

    override fun insert(entity: EmployeeEntity) {
        val params = mapOf(
            "uuid_param" to entity.uuid,
            "name_param" to entity.name,
            "email_param" to entity.email,
        )
        val result = namedParameterJdbcTemplate.update(INSERT, params)
        if (result != 1) {
            throw RuntimeException()
        }
    }

    override fun upsert(entity: EmployeeEntity) {
        val params = mapOf(
            "uuid_param" to entity.uuid,
            "name_param" to entity.name,
            "email_param" to entity.email,
        )
        val result = namedParameterJdbcTemplate.update(UPSERT, params)
        if (result != 1) {
            throw RuntimeException()
        }
    }

    override fun findByUuid(uuid: UUID): Optional<EmployeeEntity> {
        val params = mapOf("employeeUuid" to uuid)
        val result = namedParameterJdbcTemplate.query(FIND_BY_ID, params, EmployeeEntity.Mapper())
        return if (result.isEmpty())
            Optional.empty()
        else
            Optional.of(result[FIRST])
    }
}
