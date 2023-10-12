package pl.kubiczak.test.spring.integration.demo.server.employees.jpa

import java.util.*

interface EmployeeRepository {

    fun save(entity: EmployeeEntity): EmployeeEntity

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>

}
