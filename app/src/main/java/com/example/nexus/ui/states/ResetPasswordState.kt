package com.example.nexus.ui.states

data class ResetPasswordState(
    val isSuccess: Boolean = false,
    val warningMessage: String? = null,
    val isLoading: Boolean = false
)