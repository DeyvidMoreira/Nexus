package com.example.nexus.core.service.repository.local.validationFields

import android.util.Log
import android.util.Patterns
import com.example.nexus.core.service.repository.local.ValidationError
import com.google.firebase.auth.FirebaseAuth

object InputValidation {

    fun validateName(name: String): ValidationError? {
        if (name.isBlank() || name.startsWith(" ") || name.endsWith(" ")) {
            return ValidationError.INVALID_NAME
        }
        if (!name.matches(Regex("^[a-zA-Z\\s]+\$"))) {
            return ValidationError.INVALID_NAME
        }
        return null
    }

    fun validateFields(vararg fields: String): ValidationError? {
        fields.forEach { field ->
            if (field.isBlank()) {
                return ValidationError.EMPTY_FIELDS
            }
        }
        return null
    }

    fun validateEmail(email: String): ValidationError? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationError.INVALID_EMAIL
        }
        return null
    }

    fun validatePassword(password: String): ValidationError? {
        if (password.isEmpty()) {
            return ValidationError.PASSWORD_EMPTY
        }
        if (password.length < 8) {
            return ValidationError.PASSWORD_TOO_SHORT
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {
            return ValidationError.PASSWORD_NO_UPPERCASE
        }
        if (!password.matches(".*[a-z].*".toRegex())) {
            return ValidationError.PASSWORD_NO_LOWERCASE
        }
        if (!password.matches(".*[0-9].*".toRegex())) {
            return ValidationError.PASSWORD_NO_NUMBER
        }
        if (!password.matches(".*[!@#$%^&*()].*".toRegex())){
            return ValidationError.PASSWORD_NO_SPECIAL_CHAR
        }
        return null
    }



    fun validateRepeatPassword(password: String?, confirmPassword: String?): ValidationError? {
        if (password != confirmPassword) {
            return ValidationError.PASSWORDS_DO_NOT_MATCH
        }
        return null
    }

    fun validateEmptyFields(vararg fields: String): ValidationError? {
        fields.forEach { field ->
            if (field.isBlank()) {
                return ValidationError.EMPTY_FIELDS
            }
        }
        return null
    }
}