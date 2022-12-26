package pl.kubiczak.test.spring.integration.demo.config

import groovy.json.JsonSlurper
import pl.kubiczak.test.spring.integration.demo.TestcontainersSpringBaseTest

class SpringDocConfigSpec extends TestcontainersSpringBaseTest {

    private static final SWAGGER_CONFIG_PATH = '/v3/api-docs/swagger-config'

    private static final API_CONFIG_PATH = '/v3/api-docs'

    private String baseUrl

    def setup() {
        baseUrl = "http://localhost:${localServerPort}"
    }

    def "Swagger UI should be available at default path"() {
        given:
        def url = new URL("$baseUrl/swagger-ui/index.html")

        when:
        def okHtmlResponse = url.getText()

        then:
        okHtmlResponse != null
        okHtmlResponse != ''
    }

    def "Swagger endpoints description should be available as JSON"() {
        given:
        def url = new URL("$baseUrl$SWAGGER_CONFIG_PATH")

        when:
        def responseBody = url.getText()
        def json = new JsonSlurper().parseText(responseBody)

        then:
        json.url == API_CONFIG_PATH
    }

    def "Swagger API definition should be available as JSON"() {
        given:
        def url = new URL("$baseUrl$API_CONFIG_PATH")

        when:
        def responseBody = url.getText()
        def json = new JsonSlurper().parseText(responseBody)

        then:
        json.openapi == '3.0.1'
        and:
        json.info.title == 'Integration Tests Samples'
    }
}
