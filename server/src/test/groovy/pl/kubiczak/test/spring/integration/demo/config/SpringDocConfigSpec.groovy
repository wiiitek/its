package pl.kubiczak.test.spring.integration.demo.config

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import pl.kubiczak.test.spring.integration.demo.TestcontainersSpringBaseTest
import spock.lang.Ignore

@Ignore("http-builder is not supported in Groovy 4")
class SpringDocConfigSpec extends TestcontainersSpringBaseTest {

    private static final SWAGGER_CONFIG_PATH = '/v3/api-docs/swagger-config'

    private static final API_CONFIG_PATH = '/v3/api-docs'

    private RESTClient restClient

    def setup() {
        restClient = new RESTClient("http://localhost:${localServerPort}")
    }

    def "Swagger UI should be available at default path"() {
        when:
        def response = restClient.get(path: '/swagger-ui/index.html') as HttpResponseDecorator

        then:
        response.status == 200
    }

    def "Swagger endpoints description should be available as JSON"() {
        when:
        def response = restClient.get(path: SWAGGER_CONFIG_PATH) as HttpResponseDecorator

        then:
        response.status == 200
        and:
        response.data.url == API_CONFIG_PATH
    }

    def "Swagger API definition should be available as JSON"() {
        when:
        def response = restClient.get(path: API_CONFIG_PATH) as HttpResponseDecorator

        then:
        response.status == 200
        and:
        response.data.openapi == '3.0.1'
        and:
        response.data.info.title == 'Integration Tests Samples'
    }
}
