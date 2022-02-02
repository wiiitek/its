package pl.kubiczak.test.spring.integration.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import pl.kubiczak.test.spring.integration.demo.cats.CatFactClient
import pl.kubiczak.test.spring.integration.demo.cats.CatFactClientSimple

@Configuration
@PropertySource("classpath:cats.properties")
class CatFactConfig {

    @Bean
    fun catFactClient(@Value("\${apiDomain}") apiDomain: String): CatFactClient =
        CatFactClientSimple(apiDomain)
}
