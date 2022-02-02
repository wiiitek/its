package pl.kubiczak.test.spring.integration.demo.cats

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import pl.kubiczak.test.spring.integration.demo.cats.CatFactClient.CatFactDto

class CatFactClientSimple: CatFactClient {

    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://catfact.ninja")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    override fun next(): CatFactDto = webClient
        .get()
        .uri("/fact")
        .retrieve()
        .bodyToMono(CatFactDto::class.java)
        .block()!!
}
