package pl.kubiczak.test.spring.integration.demo.server.cats.ports

fun interface CatFactClient {

    fun nextFact(): CatFactDto

    data class CatFactDto(
        val fact: String,
        val length: Long
    )
}
