package pl.kubiczak.test.spring.integration.demo.cats

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import pl.kubiczak.test.spring.integration.demo.config.CatFactConfig
import spock.lang.Specification

@ContextConfiguration(
        classes = [CatFactConfig.class]
)
class CatFactClientSpec extends Specification {

    @Autowired
    private CatFactClient catFactClient

    def "should retrieve random fact about cats"() {

        expect:
        catFactClient.next() == null
    }
}
