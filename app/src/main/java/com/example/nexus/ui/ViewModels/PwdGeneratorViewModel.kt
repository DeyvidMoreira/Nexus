package com.example.nexus.ui.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.framework.service.local.repository.PasswordRepository
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.until.PasswordValidator
import com.example.nexus.ui.until.WarningMessage
import com.example.pwdcripto.framework.contants.ConstantsCharacters
import com.example.pwdcripto.framework.contants.ConstantsMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PwdGeneratorViewModel(private val passwordRepository: PasswordRepository) : ViewModel() {
    private val _state = MutableStateFlow(GeneratorState())
    val state: StateFlow<GeneratorState> = _state.asStateFlow()

    val passwords = passwordRepository.allPasswords.asLiveData()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            WarningMessage.message.collect { message ->
                _state.update { currentState ->
                    currentState.copy(warningMessage = message)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredPasswords = _searchQuery.flatMapLatest { query ->
        if (query.isEmpty()) {
            passwordRepository.allPasswords
        } else {
            passwordRepository.getPasswordsByTag(query)
        }
    }.asLiveData()

    fun updateOption(option: String, isChecked: Boolean) {
        _state.update { currentState ->
            when (option) {
                "upper" -> currentState.copy(upperChecked = isChecked)
                "lower" -> currentState.copy(lowChecked = isChecked)
                "number" -> currentState.copy(numChecked = isChecked)
                "special" -> currentState.copy(especialChecked = isChecked)
                else -> currentState
            }
        }
    }

    fun updatePasswordLength(length: Int) {
        _state.update { currentState ->
            currentState.copy(passwordLength = length, sliderValue = length.toFloat())
        }
    }

    fun generatePassword() {
        val chars = buildString {
            if (_state.value.upperChecked) append(ConstantsCharacters.UPPER_CASE_COMPLETED)
            if (_state.value.lowChecked) append(ConstantsCharacters.LOWER_CASE_COMPLETED)
            if (_state.value.numChecked) append(ConstantsCharacters.NUMBERS)
            if (_state.value.especialChecked) append(ConstantsCharacters.SPECIAL_CHARACTERS)
        }

        if (chars.isEmpty()) {
            WarningMessage.setMessage(ConstantsMessages.MESSAGE_NO_SELECTED_OPTION)
            return
        }

        if (_state.value.passwordLength < 1) {
            WarningMessage.setMessage(ConstantsMessages.MESSAGE_NO_PASSWORD_LENGTH)
            return
        }

        val password = (1.._state.value.passwordLength)
            .map { chars.random() }
            .joinToString("")

        _state.update { currentState ->
            currentState.copy(
                generatedPassword = password,
                warningMessage = null
            )
        }
    }

    fun savePassword(tag: String, password: String) {
        val error = PasswordValidator.validate(tag, _state.value.generatedPassword ?: "")
        val generatedPassword = _state.value.generatedPassword
        if (generatedPassword.isNullOrEmpty()) {
            WarningMessage.setMessage(ConstantsMessages.MESSAGE_NO_GENERATE_PASSWORD)
            return
        }
        if (tag.isEmpty()) {
            WarningMessage.setMessage(ConstantsMessages.MESSAGE_NO_TAG)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                passwordRepository.savePassword(
                    PasswordEntity(
                        tag = tag,
                        password = _state.value.generatedPassword ?: ""
                    )
                )
                _state.update { currentState ->
                    currentState.copy(isPasswordSaved = true)
                }
                WarningMessage.setMessage(ConstantsMessages.MESSAGE_PASSWORD_SAVED,)
            } catch (e: Exception) {
                Log.e("PwdGeneratorViewModel", ConstantsMessages.MESSAGE_PASSWORD_NOT_SAVED, e)
                WarningMessage.setMessage(ConstantsMessages.MESSAGE_PASSWORD_NOT_SAVED)
            }
        }
    }

    // Função para buscar senhas filtradas pela tag
    fun getPasswordsByTag(query: String) {
        _searchQuery.value = query
    }

    // Função para deletar uma senha
    fun deletePassword(password: PasswordEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordRepository.deletePassword(password)
        }
    }
}
