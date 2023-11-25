package pl.kubiczak.test.spring.integration.demo.server.cats.persistence

import pl.kubiczak.test.spring.integration.demo.server.cats.CatResponseDto
import java.util.UUID

interface CatsRepository {

    fun findAll(): List<CatResponseDto>

    fun create(name: String?, fact: String): UUID
}
