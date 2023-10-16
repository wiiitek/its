package pl.kubiczak.test.spring.integration.demo.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class BaseClientApplicationSpec extends Specification {

    @Autowired
    private ApplicationContext context

    def "should create Spring test context"() {
        expect:
        context != null
    }
}
