package com.example.nexus.ui.states

data class GeneratorState(
    val upperChecked: Boolean = false,
    val onUpperChange: (Boolean) -> Unit = {},
    val lowChecked: Boolean = false,
    val onLowChange: (Boolean) -> Unit = {},
    val numChecked: Boolean = false,
    val onNumChange: (Boolean) -> Unit = {},
    val especialChecked: Boolean = false,
    val onEspecialChange: (Boolean) -> Unit = {},
    val passwordLength: Int = 0,
    val passwordLengthChange: (Int) -> Unit = {},
    val sliderValue: Float = 0f,
    val sliderValueChange: (Float) -> Unit = {},
    val generatedPassword: String? = null,
    val onGeneratePassword: () -> Unit = {},
    val generatePassword: () -> Unit = {},
    var isPasswordSaved: Boolean = false,
    var isPasswordCopied: Boolean = false,
    var isPasswordDeleted: Boolean = false,
    var isAccountDeleted: Boolean = false,
    val warningMessage: String? = null,
)