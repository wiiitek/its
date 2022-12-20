
package pl.kubiczak.test.spring.integration.demo.config

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import pl.kubiczak.test.spring.integration.demo.TestcontainersSpringBaseTest
import spock.lang.Ignore

@Ignore("Ignoring, because for now http-builder (RESTClient) doesn't work with Groovy 4")
class SwaggerSpec extends TestcontainersSpringBaseTest {

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
        def response = restClient.get(path: '/v3/api-docs/swagger-config') as HttpResponseDecorator

        then:
        response.status == 200
        and:
        response.data.url == '/v3/api-docs'
    }

    def "API definition should be available as JSON"() {
        when:
        def response = restClient.get(path: '/v3/api-docs') as HttpResponseDecorator

        then:
        response.status == 200
        and:
        response.data.info.title == 'Integration Tests Samples'
        and:
        response.data.openapi == '3.0.1'
        and:
        response.data.paths.size() > 1
    }
}
