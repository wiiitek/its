package pl.kubiczak.test.spring.integration.demo.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
@AutoConfigureStubRunner(
        ids = "pl.kubiczak.test.spring.integration:server:+:stubs:8080",
        stubsMode = StubRunnerProperties.StubsMode.CLASSPATH
)
class BaseClientApplicationSpec extends Specification {

    @Autowired
    private ApplicationContext context

    def "should create Spring test context"() {
        expect:
        context != null
    }
}
