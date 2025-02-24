package com.example.nexus.ui.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.states.SignInState
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

class SignInViewModel(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState())
    val uiState = _uiState.asStateFlow()

    private val _singInIsSuccessful = MutableSharedFlow<Boolean>()
    val singInIsSuccessful = _singInIsSuccessful.asSharedFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
            val savedEmail = userPreferences.getSavedEmail()
            val savedPassword = userPreferences.getSavedPassword()
            val rememberMe = userPreferences.getRememberMe()

            _uiState.value = _uiState.value.copy(
                email = savedEmail,
                password = savedPassword,
                onEmailChange = { email -> onEmailChange(email) },
                onPasswordChange = { password -> onPasswordChange(password) },
                isRememberMeChecked = rememberMe,
                onRememberMeClick = { onRememberMeToggle() }
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
                firebaseAuthRepository.singIn(_uiState.value.email, _uiState.value.password)
                _uiState.update { currentState -> currentState.copy(isSuccessful = true) }
                _singInIsSuccessful.emit(true)

                if (_uiState.value.isRememberMeChecked) {
                    userPreferences.savedUserCredentials(email, password)
                } else {
                    userPreferences.clearUserCredentials()
                }

            } catch (e: Exception) {
                Log.e("singIn", "singIn: ", e)
                _uiState.update { currentState -> currentState.copy(isSuccessful = false) }
                WarningMessage.setMessage(ConstantsMessages.MESSAGE_INVALID_USER)
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
        _uiState.update { currentState ->
            val newCheckedState = !currentState.isRememberMeChecked
            viewModelScope.launch {
                if (newCheckedState) {
                    userPreferences.savedUserCredentials(currentState.email, currentState.password)
                } else {
                    userPreferences.clearUserCredentials()
                }
            }
            currentState.copy(isRememberMeChecked = newCheckedState)
        }
    }
}