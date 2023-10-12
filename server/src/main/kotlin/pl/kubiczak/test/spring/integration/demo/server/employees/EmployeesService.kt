package pl.kubiczak.test.spring.integration.demo.server.employees

import org.springframework.stereotype.Service
import pl.kubiczak.test.spring.integration.demo.server.employees.jdbc.EmployeeEntity
import pl.kubiczak.test.spring.integration.demo.server.employees.jdbc.EmployeeRepository
import java.util.*

@Service
class EmployeesService(
    private val employeeRepository: EmployeeRepository
) {
    fun findAll(): List<EmployeeDto> =
        employeeRepository.findAll()
            .map { it.toDto() }

    fun findByUuid(employeeId: UUID): Optional<EmployeeDto> =
        employeeRepository.findByUuid(employeeId)
            .map { it.toDto() }

    private fun EmployeeEntity.toDto() =
        EmployeeDto(
            uuid = this.uuid,
            name = this.name,
            email = this.email,
        )
}
