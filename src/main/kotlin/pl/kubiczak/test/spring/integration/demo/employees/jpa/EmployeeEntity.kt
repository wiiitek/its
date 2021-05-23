package pl.kubiczak.test.spring.integration.demo.employees.jpa

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "employees")
data class EmployeeEntity(
    @Id
    val id: Int?,
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    val uuid: UUID,
    val name: String,
    val email: String?
)
