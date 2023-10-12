package pl.kubiczak.test.spring.integration.demo.client

import org.springframework.beans.factory.annotation.Autowired

class EmployeeClientSpec extends BaseClientApplicationSpec {

    @Autowired
    private EmployeeClient employeeClient

    def "should create employee client with Retrofit"() {
        expect:
        employeeClient != null
        and:
        employeeClient instanceof EmployeeClient
    }
}
