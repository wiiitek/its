package pl.kubiczak.test.spring.integration.demo.cats

interface CatFactClient {

    fun nextFact(): CatFactDto

    data class CatFactDto(
        val fact: String,
        val length: Long
    )
}
