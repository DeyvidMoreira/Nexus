package com.example.nexus.ui.states

data class SignUpUiState (
    var user: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var onUserChange: (String) -> Unit = {},
    var onEmailChange: (String) -> Unit = {},
    var onPasswordChange: (String) -> Unit = {},
    var onConfirmPasswordChange: (String) -> Unit = {},
    var onSignUpClick: () -> Unit = {},
    var isLoading: Boolean = false,
    var isPasswordVisible: Boolean = false,
    var error: String? = null
)