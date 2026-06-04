package com.example.nexus.ui.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.framework.common.validation.ValidationError
import com.example.nexus.framework.common.validation.InputValidation
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.states.SignUpUiState
import com.example.nexus.ui.until.WarningMessage
import com.example.nexus.framework.common.constants.ConstantsMessages
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.suspendCoroutine

@Suppress("UNREACHABLE_CODE")
class SignUpViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val iuState = _uiState.asStateFlow()

    private val _signUpIsSuccessful = MutableSharedFlow<Boolean>()
    val signUpIsSuccessful = _signUpIsSuccessful.asSharedFlow()

    init {
        viewModelScope.launch{
            WarningMessage.message.collect{ message ->
                _uiState.update { currentState ->
                    currentState.copy(warningMessage = message)
                }
            }
        }
        _uiState.update { currentState ->
            currentState.copy(
                onUserChange = { user ->
                    _uiState.update { it.copy(user = user) }
                },
                onEmailChange = { email ->
                    _uiState.update { it.copy(email = email) }
                },
                onPasswordChange = { password ->
                    _uiState.update { it.copy(password = password) }
                },
                onConfirmPasswordChange = { password ->
                    _uiState.update { it.copy(confirmPassword = password) }
                }
            )
        }
    }

    suspend fun signUp() {
        val email = _uiState.value.email
        //Realiza as validações
        val validationError = InputValidation.validateEmptyFields(
            _uiState.value.user,
            _uiState.value.email,
            _uiState.value.password,
            _uiState.value.confirmPassword
        )
            ?: InputValidation.validateName(_uiState.value.user)
            ?: InputValidation.validateEmail(_uiState.value.email)
            ?: InputValidation.validatePassword(_uiState.value.password)
            ?: InputValidation.validateRepeatPassword(
                _uiState.value.password,
                _uiState.value.confirmPassword
            )
            ?: InputValidation.validateFields(
                _uiState.value.user,
                _uiState.value.email,
                _uiState.value.password,
                _uiState.value.confirmPassword
            )

        if (validationError != null) {
            val errorMessage = getValidationErrorMessage(validationError)
            WarningMessage.setMessage(errorMessage)

            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {

                firebaseAuthRepository.signUp(_uiState.value.email, _uiState.value.password)

                _uiState.update { currentState ->
                    currentState.copy(isSuccessful = true)
                }

                WarningMessage.setMessage(ConstantsMessages.MESSAGE_USER_SAVED)

                delay(2000)

                _signUpIsSuccessful.emit(true)

            }catch (e: Exception){
                Log.e("SignUpViewModel", "signUp: ", e)
                _uiState.update { currentState ->
                    currentState.copy(isSuccessful = false)
                }
                WarningMessage.setMessage(e.message)
            }
        }

    }

    private suspend fun checkIfEmailExists(email: String): Boolean {
        return try {
            if (email.isBlank()) return false

            val result = FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email).await()
            val signInMethods = result.signInMethods ?: emptyList()
            signInMethods.isNotEmpty()
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "checkIfEmailExists: ", e)
            false
        }
    }

    //Função para obter a mensagem de erro de validação correspondente
    private fun getValidationErrorMessage(error: ValidationError): String {
        return when (error) {
            ValidationError.PASSWORD_EMPTY -> "A senha não pode ser vazia"
            ValidationError.PASSWORD_TOO_SHORT -> "A senha deve ter pelo menos 8 caracteres"
            ValidationError.PASSWORD_NO_UPPERCASE -> "A senha deve ter pelo menos uma letra maiúscula"
            ValidationError.PASSWORD_NO_LOWERCASE -> "A senha deve ter pelo menos uma letra minúscula"
            ValidationError.PASSWORD_NO_NUMBER -> "A senha deve ter pelo menos um número"
            ValidationError.PASSWORD_NO_SPECIAL_CHAR -> "A senha deve ter pelo menos um caractere especial"
            ValidationError.PASSWORDS_DO_NOT_MATCH -> "As senhas não coincidem"
            ValidationError.INVALID_NAME -> "Nome inválido"
            ValidationError.INVALID_EMAIL -> "Email inválido"
            ValidationError.EMPTY_FIELDS -> "Preencha todos os campos"
        }
    }


}