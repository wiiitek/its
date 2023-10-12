package pl.kubiczak.test.spring.integration.demo.server.employees.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepositoryJpa : EmployeeRepository, JpaRepository<EmployeeEntity, Int> {
}
