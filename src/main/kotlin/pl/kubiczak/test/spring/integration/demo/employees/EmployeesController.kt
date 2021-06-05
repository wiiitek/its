package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeesController {

    @GetMapping(path = ["/employees"])
    fun readAll(): ResponseEntity<List<EmployeeDto>> {
        val list = emptyList<EmployeeDto>()
        return ResponseEntity.ok(list)
    }
}
