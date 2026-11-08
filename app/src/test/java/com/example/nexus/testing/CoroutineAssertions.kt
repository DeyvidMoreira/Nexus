package com.example.nexus.testing

fun eventually(
    timeoutMillis: Long = 1_000,
    assertion: () -> Boolean
) {
    val deadline = System.nanoTime() + timeoutMillis * 1_000_000L
    while (!assertion()) {
        if (System.nanoTime() >= deadline) {
            throw AssertionError("Condition not met within ${timeoutMillis}ms")
        }
        Thread.sleep(10)
    }
}
