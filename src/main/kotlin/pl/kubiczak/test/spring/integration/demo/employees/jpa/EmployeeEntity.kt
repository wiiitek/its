package pl.kubiczak.test.spring.integration.demo.employees.jpa

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "employees")
data class EmployeeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_gen")
    @SequenceGenerator(name = "employees_gen", sequenceName = "employees_seq", initialValue = 1000, allocationSize = 10)
    val id: Int?,
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    val uuid: UUID,
    val name: String,
    val email: String?
)
