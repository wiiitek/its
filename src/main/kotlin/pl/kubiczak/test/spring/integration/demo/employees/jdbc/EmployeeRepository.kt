package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import java.util.*

interface EmployeeRepository {

    fun insert(entity: EmployeeEntity)

    fun upsert(entity: EmployeeEntity)

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>
}
