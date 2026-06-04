package com.example.nexus.ui.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.framework.service.local.repository.PasswordRepository
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.until.PasswordValidator
import com.example.nexus.ui.until.WarningMessage
import com.example.nexus.framework.common.constants.ConstantsCharacters
import com.example.nexus.framework.common.constants.ConstantsMessages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PwdGeneratorViewModel(
    private val passwordRepository: PasswordRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {
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

    //Salvar senha usando AES
    fun savePassword(tag: String, password: String) {
        val error = PasswordValidator.validate(tag, password)
        if (error != null) {
            WarningMessage.setMessage(error)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                passwordRepository.savePassword(
                    PasswordEntity(
                        tag = tag,
                        password = password
                    )
                )
                WarningMessage.setMessage(ConstantsMessages.MESSAGE_PASSWORD_SAVED)
            } catch (e: Exception) {
                Log.e("PwdGeneratorViewModel", ConstantsMessages.MESSAGE_PASSWORD_NOT_SAVED, e)
                WarningMessage.setMessage(ConstantsMessages.MESSAGE_PASSWORD_NOT_SAVED)
            }

        }
    }

    // Buscar senhas filtradas pela tag
    fun getPasswordsByTag(query: String) {
        _searchQuery.value = query
    }

    // Editar uma senha
    fun editPassword(password: PasswordEntity) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                passwordRepository.updatePassword(password)
            }
        } catch (e: Exception) {
            Log.e("PwdGeneratorViewModel", "Erro ao editar senha", e)
        }
    }

    // Função para deletar uma senha
    fun deletePassword(password: PasswordEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordRepository.deletePassword(password)
        }
        WarningMessage.setMessage(ConstantsMessages.MESSAGE_PASSWORD_DELETED)
    }

    //Função para Exibir a senha descriptografada
    fun getDecryptedPassword(passwordEntity: PasswordEntity): String {
        return passwordEntity.password
    }

    // Função para deletar todas as senhas
    fun deleteAllPasswords() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                passwordRepository.deleteAllPasswords()
            }
        } catch (e: Exception) {
            Log.e("PwdGeneratorViewModel", "Erro ao deletar todas as senhas", e)
        }
        WarningMessage.setMessage(ConstantsMessages.MESSAGE_ALL_PASSWORD_DELETED)
    }

    fun deleteAccount() {
        viewModelScope.launch {
            try {
                firebaseAuthRepository.deleteAccount()
                passwordRepository.deleteAllPasswords()
                firebaseAuthRepository.logout()
                WarningMessage.setMessage(
                    ConstantsMessages.MESSAGE_ACCOUNT_DELETED
                )
            } catch (e: Exception) {
                Log.e("PwdGeneratorViewModel", "Erro ao deletar conta", e)
            }
        }
    }

    // Função Logout
    fun logout() {
        viewModelScope.launch {
            try {
                firebaseAuthRepository.logout()
            } catch (e: Exception) {
                Log.e("PwdGeneratorViewModel", "Erro ao fazer logout", e)
            }
        }
    }


}





