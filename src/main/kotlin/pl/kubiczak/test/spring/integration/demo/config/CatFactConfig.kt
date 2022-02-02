package pl.kubiczak.test.spring.integration.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.kubiczak.test.spring.integration.demo.cats.CatFactClient
import pl.kubiczak.test.spring.integration.demo.cats.CatFactClientSimple

@Configuration
class CatFactConfig {

    @Bean
    fun catFactClient(): CatFactClient =
        CatFactClientSimple()
}
