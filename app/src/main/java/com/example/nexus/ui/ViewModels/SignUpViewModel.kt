package com.example.nexus.ui.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nexus.core.service.repository.local.ValidationError
import com.example.nexus.core.service.repository.local.validationFields.InputValidation
import com.example.nexus.service.repositories.remote.FirebaseAuthRepository
import com.example.nexus.ui.states.SignUpUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(private val firebaseAuthRepository: FirebaseAuthRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val iuState = _uiState.asStateFlow()
    private val _signUpIsSuccessful = MutableSharedFlow<Boolean>()
    val signUpIsSuccessful = _signUpIsSuccessful.asSharedFlow()

    init {
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
        // Se houver erro de validação, exiba-o e interrompa o cadastro
        if (validationError != null) {
            val errorMessage = getValidationErrorMessage(validationError)
            _uiState.update {
                it.copy(error = errorMessage)

            }
            //Limpa a mensagem de erro após 3 segundos
            delayMessageAndClearError()
            return
        }
        // Se não houver erro de validação, prossiga com o cadastro
        try {
            firebaseAuthRepository
                .signUp(
                    _uiState.value.email,
                    _uiState.value.password
                )
            _signUpIsSuccessful.emit(true)
        } catch (e: Exception) {
            Log.e("SignUpViewModel", "signUp: ", e)
            _uiState.update {
                it.copy(
                    error = "Erro ao cadastrar usuário"
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    error = null
                )
            }
        }
    }

    //Função para obter a mensagem de erro de validação correspondente
    fun getValidationErrorMessage(error: ValidationError): String {
        return when (error) {
            ValidationError.PASSWORD_EMPTY -> "A senha não pode ser vazia"
            ValidationError.PASSWORD_TOO_SHORT -> "A senha deve ter pelo menos 8 caracteres"
            ValidationError.PASSWORD_NO_UPPERCASE -> "A senha deve ter pelo menos uma letra maiúscula"
            ValidationError.PASSWORD_NO_LOWERCASE -> "A senha deve ter pelo menos uma letra minúscula"
            ValidationError.PASSWORD_NO_NUMBER -> "A senha deve ter pelo menos um número"
            ValidationError.PASSWORDS_DO_NOT_MATCH -> "As senhas não coincidem"
            else -> "Erro desconhecido"
        }
    }

    //Função para exibir a mensagem de erro por 3 segundos depois limpar
    private suspend fun delayMessageAndClearError(){
        delay(3000)
        _uiState.update {
            it.copy(error = null)
        }
    }

}