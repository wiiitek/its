package pl.kubiczak.test.spring.integration.demo.cats

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import pl.kubiczak.test.spring.integration.demo.config.CatFactConfig
import spock.lang.Specification

@ContextConfiguration(classes = [CatFactConfig.class])
@TestPropertySource(properties = ["apiDomain=http://localhost:8765"])
class CatFactClientSpec extends Specification {

    @Autowired
    private CatFactClient catFactClient

    def "should retrieve random fact about cats"() {
        expect:
        catFactClient.nextFact() != null
    }

    def "should contain correct length for the retrieved fact"() {
        given:
        def factDto = catFactClient.nextFact()

        expect:
        factDto.fact.length() == factDto.length
    }
}
