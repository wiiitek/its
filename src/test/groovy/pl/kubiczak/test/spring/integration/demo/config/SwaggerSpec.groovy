package pl.kubiczak.test.spring.integration.demo.config

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import pl.kubiczak.test.spring.integration.demo.TcSpringBaseTest

class SwaggerSpec extends TcSpringBaseTest {

    private RESTClient restClient

    def setup() {
        restClient = new RESTClient("http://localhost:${localServerPort}")
    }

    def "Swagger UI should be available at default path"() {
        when:
        def response = restClient.get(path: '/swagger-ui/') as HttpResponseDecorator

        then:
        response.status == 200
    }

    def "Swagger endpoints description should be available as JSON"() {
        when:
        def response = restClient.get(path: '/v2/api-docs') as HttpResponseDecorator

        then:
        response.status == 200
        and:
        response.data.swagger == '2.0'
    }
}
