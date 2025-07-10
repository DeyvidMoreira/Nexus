package com.example.nexus.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.core.service.repository.local.ValidationError
import com.example.nexus.core.service.repository.local.validationFields.InputValidation
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.states.ResetPasswordState
import com.example.nexus.ui.until.WarningMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _resetState = MutableStateFlow(ResetPasswordState())
    val resetState: StateFlow<ResetPasswordState> = _resetState.asStateFlow()

    fun onSendResetPasswordClick(email: String) {
        val error = InputValidation.validateEmail(email)
        if (error != null) {
            val message = mapValidationErrorToMessage(error)
            WarningMessage.setMessage(message)
            _resetState.value = ResetPasswordState(isSuccess = false)
            return
        }

        _resetState.value = ResetPasswordState(isLoading = true)

        viewModelScope.launch {
            try {
                authRepository.resetPassword(email)
                WarningMessage.setMessage("E-mail enviado com sucesso!")
                _resetState.value = ResetPasswordState(isSuccess = true)
            } catch (e: Exception) {
                WarningMessage.setMessage(e.message ?: "Erro ao enviar e-mail")
                _resetState.value = ResetPasswordState(isSuccess = false)
            }
        }
    }

    fun clearState() {
        _resetState.value = ResetPasswordState()
    }

    fun mapValidationErrorToMessage(error: ValidationError): String {
        return when (error) {
            ValidationError.INVALID_EMAIL -> "Formato de e-mail inválido"
            ValidationError.EMPTY_FIELDS -> "Digite seu e-mail para continuar"
            else -> "Erro de validação"
        }
    }
}
