package pl.kubiczak.test.spring.integration.demo.client

import java.util.UUID

private const val maxSupportedInt = 999999999999

fun Long.toUUID(): UUID {
    check(this in 0..maxSupportedInt) {
        "Can only convert to UUID integer value from 0 to $maxSupportedInt"
    }
    return this.toString().padStart(11, '0')
        .let { lastPart ->
            UUID.fromString("00000000-0000-4000-a000-$lastPart")
        }
}
