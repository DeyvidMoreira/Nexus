package com.example.nexus.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.framework.service.remote.entity.UserModel
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.states.SignInState
import com.example.nexus.ui.until.UserPreferences
import com.example.nexus.ui.until.WarningMessage
import com.example.pwdcripto.framework.contants.ConstantsMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val repo: FirebaseAuthRepository,
    private val userPrefs: UserPreferences
) : ViewModel() {

    // Estado da tela
    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    // Evento de login bem-sucedido
    private val _signInIsSuccessful = MutableSharedFlow<Boolean>(replay = 1)
    val signInIsSuccessful = _signInIsSuccessful.asSharedFlow()

    /** Login normal com e-mail/senha */
    fun signIn(user: UserModel) {
        if (!validate(user.email, user.password)) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.signIn(user.email, user.password)
                _uiState.update { it.copy(isSuccessful = true) }
                _signInIsSuccessful.emit(true)

                // salva credenciais se o switch estiver ativo
                if (_uiState.value.isRememberMeChecked) {
                    userPrefs.savedUserCredentials(user.email, user.password)
                }
            } catch (e: Exception) {
                WarningMessage.setMessage(e.message ?: "Erro no login")
                _uiState.update { it.copy(isSuccessful = false) }
            }
        }
    }

    /** Biometria: usa credenciais salvas para fazer login */
    fun signInWithSavedCredentials() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = userPrefs.getSavedEmail()
            val pass  = userPrefs.getSavedPassword()
            if (email.isNotBlank() && pass.isNotBlank()) {
                signIn(UserModel(email, pass))
            } else {
                WarningMessage.setMessage("Nenhuma credencial salva")
            }
        }
    }

    /** Envia uma mensagem de alerta para a UI */
    fun emitWarning(msg: String) {
        WarningMessage.setMessage(msg)
    }

    /** Validação básica de campos */
    private fun validate(email: String, pass: String): Boolean {
        return if (email.isBlank() || pass.isBlank()) {
            WarningMessage.setMessage(ConstantsMessages.MESSAGE_EMPTY_FIELDS)
            false
        } else {
            true
        }
    }

    //Reseta estado de Login
    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetLoginState(){
        _signInIsSuccessful.resetReplayCache()
    }

    /** Toggle do \"Remember Me\" — atualiza flag no state */
    fun toggleRememberMe() {
        _uiState.update {
            it.copy(isRememberMeChecked = !it.isRememberMeChecked)
        }
    }

    /** Atualiza e-mail no state */
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    /** Atualiza senha no state */
    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }
}