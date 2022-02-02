package pl.kubiczak.test.spring.integration.demo.cats

import pl.kubiczak.test.spring.integration.demo.NoWebSpringBaseTest

class CatFactClientSpec extends NoWebSpringBaseTest {

    def "should retrieve random fact about cats"() {

        expect:
        1 == 2
    }
}
