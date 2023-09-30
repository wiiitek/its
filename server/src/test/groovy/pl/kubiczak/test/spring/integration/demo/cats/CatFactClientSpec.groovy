package pl.kubiczak.test.spring.integration.demo.cats

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import pl.kubiczak.test.spring.integration.demo.config.CatFactConfig
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*

@ContextConfiguration(classes = [CatFactConfig.class])
@TestPropertySource(properties = ["apiDomain=http://localhost:8080"])
class CatFactClientSpec extends Specification {

    @Autowired
    private CatFactClient catFactClient

    def setupSpec() {
        // https://www.baeldung.com/introduction-to-wiremock#2-basic-usage
        WireMockServer wireMockServer = new WireMockServer()
        wireMockServer.start()
        configureFor(8080)
    }

    private ResponseDefinitionBuilder sampleResponse() {
        return okJson("""
                        {
                            "fact": "a fact",
                            "length": 6
                        }
                        """)
    }

    def "should retrieve sample fact about cats"() {
        given:
        stubFor(
                get("/fact").willReturn(sampleResponse())
        )

        when:
        def actual = catFactClient.nextFact()

        then:
        actual != null
        actual.fact == "a fact"
    }

    def "should contain correct length for the retrieved fact"() {
        given:
        stubFor(
                get("/fact").willReturn(sampleResponse())
        )

        when:
        def actual = catFactClient.nextFact()

        then:
        def expectedLength = 6
        // calculates number of letters
        actual.fact.length() == expectedLength
        // verifies declared length
        actual.length == expectedLength
    }
}
