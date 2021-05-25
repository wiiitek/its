package pl.kubiczak.test.spring.integration.demo.employees

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class EmployeesController {

    @GetMapping(path = ["/employees"])
    fun readAll(): Flux<EmployeeDto> {
        return Flux.empty()
    }
}
