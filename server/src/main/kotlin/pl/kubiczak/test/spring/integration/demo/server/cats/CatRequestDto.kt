package pl.kubiczak.test.spring.integration.demo.server.cats

data class CatRequestDto(
    // there are cats, that don't have any name (nameless cats)
    val name: String?,
)
