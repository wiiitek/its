package pl.kubiczak.test.spring.integration.demo.server.employees.jdbc

import java.util.Optional
import java.util.UUID

interface EmployeeRepository {

    fun insert(entity: EmployeeEntity)

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>

    fun findAll(): List<EmployeeEntity>

    fun upsert(entity: EmployeeEntity)
}
