package com.example.nexus.core.service.repository.local.validationFields

import android.util.Patterns
import com.example.nexus.core.service.repository.local.ValidationError

object InputValidation {

    //Validação do nome do Usuário
    fun validateName(name: String): ValidationError? {
        if (name.isBlank() || name.startsWith(" ") || name.endsWith(" ")) {
            return ValidationError.INVALID_NAME
        }
        if (!name.matches(Regex("^[a-zA-Z\\s]+\$"))) {
            return ValidationError.INVALID_NAME
        }
        return null
    }

    //Validação do email do Usuário
    fun validateEmail(email: String): ValidationError? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationError.INVALID_EMAIL
        }
        return null
    }

    //Validação da senha do Usuário
    fun validatePassword(password: String): ValidationError? {
        if (password.length < 6 || password.length > 6) {
            return ValidationError.INVALID_PASSWORD
        }
        if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"))) {
            return ValidationError.INVALID_PASSWORD
        }
        return null
    }

    //Validação da confirmação da senha do Usuário
    fun validateRepeatPassword(password: String, repeatPassword: String): ValidationError? {
        if (password != repeatPassword) {
            return ValidationError.DIFFERENT_PASSWORDS
        }
        return null

    }

    //Validação de campos Vazios
    fun validateEmptyFields(vararg fields: String): ValidationError? {
        fields.forEach { fields ->
            if (fields.isBlank()) {
                return ValidationError.EMPTY_FIELDS
            }
        }
        return null
    }
}