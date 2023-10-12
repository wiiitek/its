package pl.kubiczak.test.spring.integration.demo.server.employees.exposed

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

object EmployeeTable : IdTable<Long>(name = "employees") {
    override val id: Column<EntityID<Long>>
        get() = long("id").autoIncrement(idSeqName = "employees_seq").entityId()

    val uuid = uuid("uuid").uniqueIndex()
    val name = varchar("name", length = 2048)
    val email = varchar("email", length = 512).nullable()
}
