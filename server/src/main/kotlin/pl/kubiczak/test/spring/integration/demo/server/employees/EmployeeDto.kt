package pl.kubiczak.test.spring.integration.demo.server.employees

import java.util.UUID

data class EmployeeDto(val uuid: UUID, val name: String, val email: String?)
