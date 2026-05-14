package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.java.javaUUID

object EmployeeTable : IdTable<Long>(name = "employees") {
    override val id: Column<EntityID<Long>>
        get() = long("id").autoIncrement(idSeqName = "employees_seq").entityId()

    val uuid = javaUUID("uuid").uniqueIndex()
    val name = varchar("name", length = 2048)
    val email = varchar("email", length = 512).nullable()
}
