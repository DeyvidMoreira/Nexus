package com.example.nexus.ui.states

data class SignInState(
    var email: String = "",
    var password: String = "",
    var isRememberMeChecked: Boolean = false,
    var onRememberMeClick: () -> Unit = {},
    var onForgotPasswordClick: () -> Unit = {},
    var onSignUpClick: () -> Unit = {},
    var onEmailChange: (String) -> Unit = {},
    var onPasswordChange: (String) -> Unit = {},
    var onSignInClick: () -> Unit = {},
    var onNavigationToSignUp: () -> Unit = {},
    var onNavigationToForgotPassword: () -> Unit = {},
    var isSuccessful: Boolean = false,
    var isLoading: Boolean = false,
    var isPasswordVisible: Boolean = false,
    var warningMessage: String? = null
)
