package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import java.util.*

interface EmployeeRepository {

    fun save(entity: EmployeeEntity)

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>
}
