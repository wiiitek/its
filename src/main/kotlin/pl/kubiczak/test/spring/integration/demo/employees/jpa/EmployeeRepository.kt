package pl.kubiczak.test.spring.integration.demo.employees.jpa

import java.util.*

interface EmployeeRepository {

    fun findByUuid(uuid: UUID): Optional<EmployeeEntity>

}
