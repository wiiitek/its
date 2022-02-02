package pl.kubiczak.test.spring.integration.demo.cats

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import pl.kubiczak.test.spring.integration.demo.config.CatFactConfig
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@ContextConfiguration(classes = [CatFactConfig.class])
@TestPropertySource(properties = ["apiDomain=http://localhost:8080"])
@AutoConfigureWireMock
class CatFactClientSpec extends Specification {

    @Autowired
    private CatFactClient catFactClient

    def "should retrieve random fact about cats"() {
        given:
        stubFor(get("/fact").willReturn(
                okJson("""
                        {
                            "fact": "a fact",
                            "length": 123
                        }
                        """)
        ))

        when:
        def actual = catFactClient.nextFact()

        then:
        actual != null
    }

    def "should contain correct length for the retrieved fact"() {
        given:
        stubFor(get("/fact").willReturn(
                okJson("""
                        {
                            "fact": "a fact",
                            "length": 6
                        }
                        """)
        ))

        when:
        def actual = catFactClient.nextFact()

        then:
        actual.fact.length() == actual.length
    }
}
