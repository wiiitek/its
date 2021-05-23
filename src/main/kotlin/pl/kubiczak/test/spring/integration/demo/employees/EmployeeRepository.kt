package pl.kubiczak.test.spring.integration.demo.employees

import java.util.*

interface EmployeeRepository {

    fun findById(id: Int): Optional<EmployeeEntity>
}
