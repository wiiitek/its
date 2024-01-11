package pl.kubiczak.test.spring.integration.demo.server.employees.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "employees")
open class EmployeeEntity(@Column(name="\"name\"") open var name: String, open var email: String?) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_gen")
    @SequenceGenerator(name = "employees_gen", sequenceName = "employees_seq", initialValue = 1000, allocationSize = 10)
    open val id: Long? = null

    open val uuid: UUID = UUID.randomUUID()

    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmployeeEntity) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
