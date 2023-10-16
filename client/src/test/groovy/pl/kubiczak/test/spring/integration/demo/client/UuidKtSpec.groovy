package pl.kubiczak.test.spring.integration.demo.client

import spock.lang.Specification

class UuidKtSpec extends Specification {

    def "should correctly convert integers to UUID"() {
        expect:
        UuidKt.toUUID(longValue) == UUID.fromString(expectedUUID)

        where:
        longValue    || expectedUUID
        0            || "00000000-0000-0000-a000-000000000000"
        1            || "00000000-0000-0000-a000-000000000001"
        999999999999 || "00000000-0000-0000-a000-999999999999"
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
}
