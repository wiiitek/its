package pl.kubiczak.test.spring.integration.demo.server.employees

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class EmployeesController(
    private val employeesService: EmployeesService
) {

    @GetMapping(path = ["/employees"])
    fun readAll(): ResponseEntity<List<EmployeeDto>> =
        ResponseEntity.ok(
            employeesService.findAll()
        )

    @GetMapping(path = ["/employees/{uuid}"])
    fun readAll(@PathVariable uuid: UUID): ResponseEntity<EmployeeDto> =
        ResponseEntity.of(
            employeesService.findByUuid(uuid)
        )
}
