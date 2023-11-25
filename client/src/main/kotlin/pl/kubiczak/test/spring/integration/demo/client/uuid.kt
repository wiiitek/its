package pl.kubiczak.test.spring.integration.demo.client

import java.util.UUID

private const val maxSupportedInt = 999999999999

// https://cloud.magiclen.org/us/uuid/checker
fun Long.toUUID(): UUID {
    check(this in 0..maxSupportedInt) {
        "Can only convert to UUID integer value from 0 to $maxSupportedInt"
    }
    return this.toString().padStart(11, '0')
        .let { lastPart ->
            // variant digit can be one of: 8, 9, a, b
            // version number needs to be 4
            // everything else is random
            // https://datatracker.ietf.org/doc/html/rfc4122#section-4.4
            UUID.fromString("00000000-0000-4000-a000-$lastPart")
        }
}
