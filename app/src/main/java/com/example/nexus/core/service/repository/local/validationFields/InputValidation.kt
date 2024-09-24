package com.example.nexus.core.service.repository.local.validationFields

import android.util.Patterns
import android.widget.Toast
import com.example.nexus.core.service.repository.local.ValidationError
/**
 * Object responsible for validating user inputs.
 */
object InputValidation {

    /**
     * Validates the user's name.
     * @param name The name entered by the user.
     * @return [ValidationError.INVALID_NAME] if the name is invalid, null otherwise.
     */
    fun validateName(name: String): ValidationError? {
        if (name.isBlank() || name.startsWith(" ") || name.endsWith(" ")) {
            return ValidationError.INVALID_NAME
        }
        if (!name.matches(Regex("^[a-zA-Z\\s]+\$"))) {
            return ValidationError.INVALID_NAME
        }
        return null
    }

    /**
     * Validates the user's email.
     * @param email The email entered by the user.
     * @return [ValidationError.INVALID_EMAIL] if the email is invalid, null otherwise.
     */
    fun validateEmail(email: String): ValidationError? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationError.INVALID_EMAIL
        }
        return null
    }

    /**
     * Validates the user's password.
     * @param password The password entered by the user.
     * @return [ValidationError.INVALID_PASSWORD] if the password is invalid, null otherwise.
     */
    fun validatePassword(password: String): ValidationError? {
        if (password.length < 6 || password.length > 6) {
            return ValidationError.INVALID_PASSWORD
        }
        if (!password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+\$"))) {
            return ValidationError.INVALID_PASSWORD
        }
        return null
    }

    /**
     * Validates if the repeated password matches the original password.
     * @param password The original password.
     * @param repeatPassword The repeated password.
     * @return [ValidationError.DIFFERENT_PASSWORDS] if the passwords do not match, null otherwise.
     */
    fun validateRepeatPassword(password: String, repeatPassword: String): ValidationError? {
        if (password != repeatPassword) {
            return ValidationError.DIFFERENT_PASSWORDS
        }
        return null
    }

    /**
     * Validates if any of the provided fields are empty.
     * @param fields List of fields to check.
     * @return [ValidationError.EMPTY_FIELDS] if any field is empty, null otherwise.
     */
    fun validateEmptyFields(vararg fields: String): ValidationError? {
        fields.forEach { field ->
            if (field.isBlank()) {
                return ValidationError.EMPTY_FIELDS
            }
        }
        return null
    }
}