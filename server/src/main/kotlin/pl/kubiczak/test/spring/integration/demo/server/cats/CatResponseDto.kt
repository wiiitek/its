package pl.kubiczak.test.spring.integration.demo.server.cats

import java.util.UUID

data class CatResponseDto(
    val id: UUID,
    // there are cats, that don't have any name (nameless cats)
    val name: String?,
    /**
     * Random fact about cats
     */
    val fact: String,
)
