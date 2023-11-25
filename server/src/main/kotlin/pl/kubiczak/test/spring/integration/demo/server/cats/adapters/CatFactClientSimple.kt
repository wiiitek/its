package pl.kubiczak.test.spring.integration.demo.server.cats.adapters

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatFactClient
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatFactClient.CatFactDto

class CatFactClientSimple(apiDomain: String) : CatFactClient {

    private val webClient: WebClient = WebClient.builder()
        .baseUrl(apiDomain)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    override fun nextFact(): CatFactDto = webClient
        .get()
        .uri("/fact")
        .retrieve()
        .bodyToMono(CatFactDto::class.java)
        .block()!!
}
