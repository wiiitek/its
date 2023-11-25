package pl.kubiczak.test.spring.integration.demo.server.cats

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class CatsController(
    private val catsService: CatsService
) {

    @GetMapping(path = ["/cats"])
    fun readAll(): ResponseEntity<List<CatResponseDto>> =
        catsService.readAll()
            .let {
                ResponseEntity.ok(it)
            }

    @PostMapping(path = ["/cats"])
    fun create(
        @RequestBody request: CatRequestDto
    ): ResponseEntity<UUID> =
        catsService.create(request.name)
            .let { ResponseEntity.ok(it) }
}
