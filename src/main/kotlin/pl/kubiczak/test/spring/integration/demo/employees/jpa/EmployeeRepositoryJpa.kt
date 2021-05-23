package pl.kubiczak.test.spring.integration.demo.employees.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("jpaRepository")
interface EmployeeRepositoryJpa : EmployeeRepository, JpaRepository<EmployeeEntity, Int> {
}
