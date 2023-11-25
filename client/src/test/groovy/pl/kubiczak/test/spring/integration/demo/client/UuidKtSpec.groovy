package pl.kubiczak.test.spring.integration.demo.client

import spock.lang.Specification

class UuidKtSpec extends Specification {

    def "should correctly convert integers to UUID"() {
        expect:
        UuidKt.toUUID(longValue) == UUID.fromString(expectedUUID)

        where:
        longValue    || expectedUUID
        0            || "00000000-0000-4000-a000-000000000000"
        1            || "00000000-0000-4000-a000-000000000001"
        999999999999 || "00000000-0000-4000-a000-999999999999"
    }

    def "should throw exception for incorrect input value"() {
        when:
        UuidKt.toUUID(invalidInput)

        then:
        def e = thrown(IllegalStateException)
        and:
        e.message == "Can only convert to UUID integer value from 0 to 999999999999"

        where:
        _ || invalidInput
        _ || -1
        _ || 1000000000000
    }

    // https://datatracker.ietf.org/doc/html/rfc4122#section-4.1.1
    def "should be IETF RFC 4122 variant of UUID"() {
        given:
        int ietfRFC4122 = 2

        when:
        UUID uuid = UuidKt.toUUID(input)

        then:
        uuid.variant() == ietfRFC4122

        where:
        _ || input
        _ || 1
        _ || 15
        _ || 999999999
        _ || 1024
    }

    // https://datatracker.ietf.org/doc/html/rfc4122#section-4.1.3
    def "should be version 4 of UUID"() {
        given:
        int expectedVersion = 4

        when:
        UUID uuid = UuidKt.toUUID(input)

        then:
        uuid.version() == expectedVersion

        where:
        _ || input
        _ || 1
        _ || 15
        _ || 999999999
        _ || 1024

    }
}
