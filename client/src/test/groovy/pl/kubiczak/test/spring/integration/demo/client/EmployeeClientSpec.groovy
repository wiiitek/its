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
        response.body()[0].uuid == UuidKt.toUUID(1)
        response.body()[0].name == "John Doe"
        response.body()[0].email == "john.doe@example.com"
        response.body()[1].uuid == UuidKt.toUUID(2)
        response.body()[1].name == "Jane Smith"
        response.body()[1].email == null
    }

    def "should receive 404 for non-existent employee"() {
        given:
        def nonExistingEmployeeID = UUID.fromString("00000000-0000-0000-0000-000000000000")

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
        def expectedEmployeeId = UUID.fromString("00000000-0000-0000-a000-000000000001")

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
