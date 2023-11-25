package pl.kubiczak.test.spring.integration.demo.client

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class EmployeeClientSpec extends BaseClientApplicationSpec {

    @Autowired
    private EmployeeClient employeeClient

    def "should receive sample list of employees"() {
        when:
        def response = employeeClient.listEmployees().execute()

        then:
        response.code() == HttpStatus.OK.value()
        and:
        response.body().size() == 2
        and:
        with(response.body()[0]) { first ->
            first.uuid == UuidKt.toUUID(1)
            first.name == "John Doe"
            first.email == "john.doe@example.com"
        }
        with(response.body()[1]) { second ->
            second.uuid == UuidKt.toUUID(2)
            second.name == "Jane Smith"
            second.email == null
        }
    }

    def "should receive 404 for non-existent employee"() {
        given:
        def nonExistingEmployeeID = UUID.randomUUID()

        when:
        def response = employeeClient.findEmployee(nonExistingEmployeeID).execute()

        then:
        response.code() == HttpStatus.NOT_FOUND.value()
        and:
        response.body() == null
    }

    def "should receive sample employee"() {
        given:
        def idIntValue = 1
        def sampleEmployeeId = UuidKt.toUUID(idIntValue)
        def expectedEmployeeId = UUID.fromString("00000000-0000-4000-a000-000000000001")

        when:
        def response = employeeClient.findEmployee(sampleEmployeeId).execute()

        then:
        response.code() == HttpStatus.OK.value()

        and:
        with(response.body()) {
            it.uuid == expectedEmployeeId
            it.name == "John Doe"
            it.email == "john.doe@example.com"
        }
    }
}
