package com.example.nexus.core.service.repository.local

/**
 * Enum class representing the different validation errors that can occur in the app.
 */
enum class ValidationError {
    INVALID_NAME,         // Nome inválido
    INVALID_EMAIL,        // Email inválido
    INVALID_PASSWORD,     // Senha inválida
    EMPTY_FIELDS,         // Campos vazios
    DIFFERENT_PASSWORDS   // Senhas diferentes
}