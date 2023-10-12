package pl.kubiczak.test.spring.integration.demo.server.employees.jdbc

import java.util.*

interface EmployeeRepository {

    fun insert(entity: EmployeeEntity)

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>

    fun findAll(): List<EmployeeEntity>

    fun upsert(entity: EmployeeEntity)
}
