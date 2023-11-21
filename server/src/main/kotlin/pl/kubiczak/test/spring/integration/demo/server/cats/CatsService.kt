package pl.kubiczak.test.spring.integration.demo.server.cats

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.api.common.Attributes
import io.opentelemetry.api.metrics.MeterProvider
import io.opentelemetry.api.trace.Tracer
import org.springframework.stereotype.Service
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatFactClient
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatsRepository
import java.util.UUID


@Service
class CatsService(
    private val catsRepository: CatsRepository,
    private val catFactClient: CatFactClient,
    private val tracer: Tracer,
    meterProvider: MeterProvider,
) {
    private val factsCounter = meterProvider
        .meterBuilder("cats-service-scope-name")
        .build()
        .counterBuilder("cat-facts-retrieved")
        .build()

    private fun attributesOf(declaredLength: Long): Attributes =
        Attributes.of(AttributeKey.longKey("declared-length"), declaredLength)


    fun readAll(): List<CatResponseDto> =
        catsRepository.findAll()

    fun create(name: String?): UUID {
        val span = tracer.spanBuilder("retrieve-and-validate-cat-fact").startSpan()

        val factContent = try {
            name?.let { span.setAttribute("cat-name", it) }
            span.makeCurrent().use {
                val response = catFactClient.nextFact()
                val content = response.fact
                val declaredLength = response.length

                factsCounter.add(1, attributesOf(declaredLength))

                check(content.length == declaredLength.toInt()) {
                    "Invalid fact length: ${response.length} for fact content: ${response.fact}"
                }
                content
            }
        } catch (e: Exception) {
            span.recordException(e)
            throw e
        } finally {
            span.end()
        }

        return catsRepository.create(name, factContent)
    }
}
