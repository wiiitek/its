package pl.kubiczak.test.spring.integration.demo.employees.jdbc

import java.util.*

interface EmployeeRepository {

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>
}
