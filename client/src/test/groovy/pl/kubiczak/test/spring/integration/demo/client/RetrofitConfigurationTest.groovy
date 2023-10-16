package pl.kubiczak.test.spring.integration.demo.client

import spock.lang.Specification

class RetrofitConfigurationTest extends Specification {

    private tested = new RetrofitConfiguration()

    def "should create client with sample domain"() {
        when:
        def createdClient = tested.employeeClient(validBaseUrl)

        then:
        createdClient != null

        where:
        _ || validBaseUrl
        _ || "https://www.example.com/some/path/"
        _ || "http://api.ab/"
    }

    def "should throw exception while creating client with incorrect domain"() {
        when:
        tested.employeeClient(invalidBaseUrl)

        then:
        IllegalArgumentException exception = thrown(IllegalArgumentException)
        and:
        exception.message.startsWith(errMessagePrefix)

        where:
        invalidBaseUrl                                            || errMessagePrefix
        "https://www.example.com/some/path/not/ending/with/slash" || "baseUrl must end in /"
        "ftp://incorrect.protocol/"                               || "Expected URL scheme 'http' or 'https' but was 'ftp'"
        "//missing.protocol/"                                     || "Expected URL scheme 'http' or 'https' but no scheme was found"
        ""                                                        || "Expected URL scheme 'http' or 'https' but no scheme was found"
    }
}
