package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.stereotype.Service
import pl.kubiczak.test.spring.integration.demo.employees.jdbc.EmployeeRepository
import java.util.*

@Service
class EmployeesService(
    private val employeeRepository: EmployeeRepository
) {
    fun findAll(): List<EmployeeDto> =
        employeeRepository.findAll()
            .map {
                EmployeeDto(
                    uuid = it.uuid,
                    name = it.name,
                    email = it.email,
                )
            }

    fun findByUuid(employeeId: UUID): Optional<EmployeeDto> =
        employeeRepository.findByUuid(employeeId)
            .map {
                EmployeeDto(
                    uuid = it.uuid,
                    name = it.name,
                    email = it.email,
                )
            }
}
