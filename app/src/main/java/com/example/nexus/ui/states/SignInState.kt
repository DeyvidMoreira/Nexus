package com.example.nexus.ui.states

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isRememberMeChecked: Boolean = false,
    val onRememberMeClick: () -> Unit = {},
    val isBiometricCheck: Boolean = false,
    val onBiometricClick: () -> Unit = {},
    val onForgotPasswordClick: () -> Unit = {},
    val onSignUpClick: () -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onSignInClick: () -> Unit = {},
    val onNavigationToSignUp: () -> Unit = {},
    val onNavigationToForgotPassword: () -> Unit = {},
    val isSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val warningMessage: String? = null
)
