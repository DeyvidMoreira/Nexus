package com.example.nexus.ui.until

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

object WarningMessage {
    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    fun setMessage(message: String?) {
        _message.value = message
        message?.let {
            CoroutineScope(Dispatchers.Main).launch {
                delay(1.seconds)
                _message.value = null
            }
        }
    }
}