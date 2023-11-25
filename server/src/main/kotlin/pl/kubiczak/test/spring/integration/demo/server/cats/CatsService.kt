package pl.kubiczak.test.spring.integration.demo.server.cats

import org.springframework.stereotype.Service
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatFactClient
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatsRepository
import java.util.UUID

@Service
class CatsService(
    private val catsRepository: CatsRepository,
    private val catFactClient: CatFactClient,
) {

    fun readAll(): List<CatResponseDto> =
        catsRepository.findAll()

    fun create(name: String?): UUID {

        val factContent = try {
            val response = catFactClient.nextFact()

            val factContent = response.fact
            val declaredLength = response.length

            check(factContent.length == declaredLength.toInt()) {
                "Invalid fact length: ${response.length} for fact content: ${response.fact}"
            }
            factContent
        } catch (e: Exception) {
            throw e
        }

        return catsRepository.create(name, factContent)
    }
}
