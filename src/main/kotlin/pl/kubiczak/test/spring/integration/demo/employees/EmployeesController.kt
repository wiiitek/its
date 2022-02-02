package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class EmployeesController {

    @GetMapping(path = ["/employees"])
    fun readAll(): ResponseEntity<List<EmployeeDto>> {
        val list = emptyList<EmployeeDto>()
        return ResponseEntity.ok(list)
    }

    @GetMapping(path = ["/employees/{uuid}"])
    fun readAll(@PathVariable uuid: UUID): ResponseEntity<EmployeeDto> {
        val anEmployee = EmployeeDto(uuid, "John Doe", "john.doe@example.com")
        return ResponseEntity.ok(anEmployee)
    }
}
