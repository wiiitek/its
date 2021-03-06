package pl.kubiczak.test.spring.integration.demo.employees.jpa

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "employees")
open class EmployeeEntity(open var name: String, open var email: String?) {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_gen")
    @SequenceGenerator(name = "employees_gen", sequenceName = "employees_seq", initialValue = 1000, allocationSize = 10)
    open val id: Long?

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    open val uuid: UUID

    // empty constructor will be generated by kotlin("plugin.jpa")
    init {
        this.id = null
        this.uuid = UUID.randomUUID()
    }

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
