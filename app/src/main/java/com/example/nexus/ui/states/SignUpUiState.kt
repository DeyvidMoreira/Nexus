package com.example.nexus.ui.states

data class SignUpUiState (
    val user: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val onUserChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onConfirmPasswordChange: (String) -> Unit = {},
    val onSignUpClick: () -> Unit = {},
    val isLoading: Boolean = false,
    val error: String? = null
)