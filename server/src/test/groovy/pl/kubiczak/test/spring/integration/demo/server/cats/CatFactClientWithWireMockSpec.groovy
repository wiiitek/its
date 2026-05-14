package pl.kubiczak.test.spring.integration.demo.server.cats


import static com.github.tomakehurst.wiremock.client.WireMock.configureFor
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.okJson
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.codec.DecodingException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import pl.kubiczak.test.spring.integration.demo.server.cats.ports.CatFactClient
import pl.kubiczak.test.spring.integration.demo.server.config.CatFactConfig
import spock.lang.Specification

@ContextConfiguration(classes = [CatFactConfig.class])
@TestPropertySource(properties = ["apiDomain=http://localhost:8080"])
class CatFactClientWithWireMockSpec extends Specification {

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

    private ResponseDefinitionBuilder invalidTypesInResponse() {
        return okJson("""
                        {
                            "fact": 12345,
                            "length": "five"
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

    def "should throw exception for invalid response"() {
        given:
        stubFor(
                get("/fact").willReturn(invalidTypesInResponse())
        )

        when:
        catFactClient.nextFact()

        then:
        def exception = thrown(DecodingException)
        and:
        exception.message == "JSON decoding error: Cannot deserialize value of type `long` from String \"five\": not a valid `long` value"
    }
}
