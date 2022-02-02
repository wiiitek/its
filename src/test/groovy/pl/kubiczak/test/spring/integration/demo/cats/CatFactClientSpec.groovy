package pl.kubiczak.test.spring.integration.demo.cats

import pl.kubiczak.test.spring.integration.demo.SpringBaseTestForHttpClient

class CatFactClientSpec extends SpringBaseTestForHttpClient {

    def "should retrieve random fact about cats"() {

        expect:
        1 == 2
    }
}
