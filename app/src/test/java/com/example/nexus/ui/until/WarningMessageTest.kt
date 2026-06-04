package com.example.nexus.ui.until

import com.example.nexus.testing.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WarningMessageTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        WarningMessage.setMessage(null)
    }

    @Test
    fun `setMessage stores message immediately`() {
        WarningMessage.setMessage("Mensagem")

        assertThat(WarningMessage.message.value).isEqualTo("Mensagem")
    }

    @Test
    fun `setMessage clears message after timeout`() = runTest {
        WarningMessage.setMessage("Mensagem")

        advanceTimeBy(1_000)
        runCurrent()

        assertThat(WarningMessage.message.value).isNull()
    }

    @Test
    fun `setMessage accepts null to clear current message`() {
        WarningMessage.setMessage("Mensagem")

        WarningMessage.setMessage(null)

        assertThat(WarningMessage.message.value).isNull()
    }
}
