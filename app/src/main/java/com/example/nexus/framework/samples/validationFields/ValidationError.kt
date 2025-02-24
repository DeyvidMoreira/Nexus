package com.example.nexus.core.service.repository.local

/**
 * Enum class representing the different validation errors that can occur in the app.
 */
enum class ValidationError {
    INVALID_NAME,
    INVALID_EMAIL,
    PASSWORD_EMPTY,
    PASSWORD_TOO_SHORT,
    PASSWORD_NO_LOWERCASE,
    PASSWORD_NO_NUMBER,
    PASSWORD_NO_UPPERCASE,
    PASSWORD_NO_SPECIAL_CHAR,
    PASSWORDS_DO_NOT_MATCH,
    EMPTY_FIELDS,
}