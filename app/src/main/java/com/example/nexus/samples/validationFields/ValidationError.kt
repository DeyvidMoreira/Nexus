package com.example.nexus.core.service.repository.local

/**
 * Enum class representing the different validation errors that can occur in the app.
 */
enum class ValidationError {
    INVALID_NAME,         // Nome inválido
    INVALID_EMAIL,        // Email inválido
    INVALID_PASSWORD,     // Senha inválida
    PASSWORD_EMPTY,
    PASSWORD_TOO_SHORT,
    PASSWORD_NO_LOWERCASE,
    PASSWORD_NO_NUMBER,
    PASSWORD_NO_UPPERCASE,
    PASSWORDS_DO_NOT_MATCH,
    EMPTY_FIELDS,         // Campos vazios
    DIFFERENT_PASSWORDS   // Senhas diferentes
}