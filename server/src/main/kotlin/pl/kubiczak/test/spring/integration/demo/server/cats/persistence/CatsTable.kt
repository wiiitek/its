package pl.kubiczak.test.spring.integration.demo.server.cats.persistence

import org.jetbrains.exposed.dao.id.UUIDTable

object CatsTable : UUIDTable("cats", "uuid") {

    val name = text("name").nullable()
    val fact = text("fact")
}
