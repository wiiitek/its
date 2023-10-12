package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import pl.kubiczak.test.spring.integration.demo.server.employees.EmployeeDto
import java.util.*

interface EmployeeRepository {

    fun create(name: String, email: String?): EmployeeDto

    fun findByUuid(uuid: UUID): EmployeeDto?

}
