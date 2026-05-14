package pl.kubiczak.test.spring.integration.demo.server.cats.adapters

import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable

object CatsTable : UUIDTable("cats", "uuid") {

    val name = text("name").nullable()
    val fact = text("fact")
}
