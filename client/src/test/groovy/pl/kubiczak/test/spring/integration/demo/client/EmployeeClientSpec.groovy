package pl.kubiczak.test.spring.integration.demo.client

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties

@SpringBootTest
@AutoConfigureStubRunner(
        ids = "pl.kubiczak.test.spring.integration:server:0.0.1-SNAPSHOT",
        stubsMode = StubRunnerProperties.StubsMode.CLASSPATH
)
class EmployeeClientSpec {
}
