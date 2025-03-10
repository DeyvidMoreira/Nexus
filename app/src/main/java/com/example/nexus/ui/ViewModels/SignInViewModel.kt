package com.example.nexus.ui.ViewModels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.states.SignInState
import com.example.nexus.ui.until.BiometricPromptManager
import com.example.nexus.ui.until.UserPreferences
import com.example.nexus.ui.until.WarningMessage
import com.example.pwdcripto.framework.contants.ConstantsMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
class SignInViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val userPreferences: UserPreferences,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    private val _signInIsSuccessful = MutableSharedFlow<Boolean>()
    val signInIsSuccessful = _signInIsSuccessful.asSharedFlow()

    init {
        viewModelScope.launch {
            val savedEmail = userPreferences.getSavedEmail()
            val savedPassword = userPreferences.getSavedPassword()
            val rememberMe = userPreferences.getRememberMe()

            _uiState.value = _uiState.value.copy(
                email = savedEmail,
                password = savedPassword,
                isRememberMeChecked = rememberMe,
                onEmailChange = { email -> onEmailChange(email) },
                onPasswordChange = { password -> onPasswordChange(password) },
                onRememberMeClick = { onRememberMeToggle() },
                onBiometricClick = { startBiometricAuthentication() }
            )

            WarningMessage.message.collect { message ->
                _uiState.update { currentState ->
                    currentState.copy(warningMessage = message)
                }
            }
        }
    }

    private fun onEmailChange(email: String) {
        _uiState.update { currentState -> currentState.copy(email = email) }
    }

    private fun onPasswordChange(password: String) {
        _uiState.update { currentState -> currentState.copy(password = password) }
    }

    fun signIn() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (!validationField(email, password)) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                firebaseAuthRepository.signIn(email, password)
                _uiState.update { currentState -> currentState.copy(isSuccessful = true) }
                _signInIsSuccessful.emit(true)

                if (_uiState.value.isRememberMeChecked) {
                    userPreferences.savedUserCredentials(email, password)
                } else {
                    userPreferences.clearUserCredentials()
                }

            } catch (e: Exception) {
                Log.e("signIn", "signIn: ", e)
                _uiState.update { currentState -> currentState.copy(isSuccessful = false) }
                WarningMessage.setMessage(e.message)
            }
        }
    }

    private fun validationField(email: String, password: String): Boolean {
        return if (email.isBlank() || password.isBlank()) {
            WarningMessage.setMessage(ConstantsMessages.MESSAGE_EMPTY_FIELDS)
            false
        } else {
            true
        }
    }

    private fun onRememberMeToggle() {
        val newCheckedState = !_uiState.value.isRememberMeChecked
        _uiState.update { it.copy(isRememberMeChecked = newCheckedState) }

        val email = _uiState.value.email
        val password = _uiState.value.password

        Log.d("SignIn", "Remember Me toggled: $newCheckedState")
        Log.d("SignIn", "Saving Email: $email")
        Log.d("SignIn", "Saving Password: $password")

        viewModelScope.launch {
            if (newCheckedState) {
                userPreferences.savedUserCredentials(email, password)
            } else {
                userPreferences.clearUserCredentials()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun startBiometricAuthentication() {
        val biometricManager = BiometricManager.from(context)
        val authResult = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )

        when (authResult) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("BiometricAuth", "Biometria disponível, exibindo prompt...")
                showBiometricPrompt()
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val errorMessage = when (authResult) {
                    BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "Biometria não disponível no dispositivo"
                    BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Biometria indisponível no momento"
                    else -> "Nenhuma biometria cadastrada"
                }
                Log.e("BiometricAuth", errorMessage)
                WarningMessage.setMessage(errorMessage)
            }
            else -> {
                Log.e("BiometricAuth", "Erro desconhecido ao verificar biometria")
                WarningMessage.setMessage("Erro desconhecido ao verificar biometria")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun showBiometricPrompt() {
        val newCheckedState = !_uiState.value.isBiometricCheck
        _uiState.update { it.copy(isBiometricCheck = newCheckedState)}

        Log.d("BiometricAuth", "showBiometricPrompt chamado")

        val biometricPromptManager = BiometricPromptManager(context = context)

        biometricPromptManager.showBiometricPrompt(
            title = "Autenticação Biométrica",
            description = "Use sua biometria para fazer login"
        )

        if (newCheckedState){
            viewModelScope.launch(Dispatchers.IO) {
                biometricPromptManager.promptResult.collect { result ->
                    Log.d("BiometricAuth", "Resultado da biometria: $result")
                    try {
                        when (result) {
                            is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                                Log.d("BiometricAuth", "Autenticação bem-sucedida!")
                                signInWithBiometric()
                            }
                            is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                                Log.e("BiometricAuth",
                                    "Erro na autenticação biométrica: ${result.error}")
                                WarningMessage.setMessage(result.error)
                            }
                            else -> {
                                Log.e("BiometricAuth", "Falha na autenticação biométrica.")
                                WarningMessage.setMessage("Falha na autenticação biométrica.")
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("BiometricAuth", "Erro na autenticação biométrica.", e)
                    }
                }
            }
        }
    }

    private fun signInWithBiometric() {
        // Realizar o login sem a necessidade de senha, se biometria for bem-sucedida
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val email = _uiState.value.email
                val password = _uiState.value.password
                firebaseAuthRepository.signIn(email, password)
                _uiState.update { currentState -> currentState.copy(isSuccessful = true) }
                _signInIsSuccessful.emit(true)
                onLoginSuccess()
            } catch (e: Exception) {
                Log.e("signIn", "signInWithBiometric: ", e)
                _uiState.update { currentState -> currentState.copy(isSuccessful = false) }
                WarningMessage.setMessage(e.message)
            }
        }
    }

    private fun onLoginSuccess() {
        // Lógica pós-sucesso de login
        Log.d("SignIn", "Login bem-sucedido!")
        // Você pode querer navegar para a tela principal aqui, por exemplo:
        // _uiState.update { currentState -> currentState.copy(isLoggedIn = true) }
    }
}