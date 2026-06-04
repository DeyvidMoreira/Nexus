package com.example.nexus.testing

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

suspend fun eventually(
    timeoutMillis: Long = 1_000,
    assertion: () -> Boolean
) {
    withTimeout(timeoutMillis) {
        while (!assertion()) {
            delay(10)
        }
    }
}
