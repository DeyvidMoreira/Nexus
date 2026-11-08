package com.example.nexus.untils

import com.example.nexus.framework.common.validation.ValidationError
import com.example.nexus.framework.common.validation.InputValidation
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class InputValidationTest {

    @Test
    fun `validateName returns INVALID_NAME when name is blank`() {
        val result = InputValidation.validateName("")
        assertEquals(ValidationError.INVALID_NAME, result)
    }

    @Test
    fun `validateName returns null for valid name`() {
        val result = InputValidation.validateName("Carl Jhoson")
        assertNull(result)
    }

    @Test
    fun `validateEmail returns INVALID_EMAIL for invalid email`() {
        val result = InputValidation.validateEmail("usuario")
        assertEquals(ValidationError.INVALID_EMAIL, result)
    }

    @Test
    fun `validateEmail returns null for valid email`() {
        val result = InputValidation.validateEmail("user@doman.com")
        assertNull(result)
    }

    @Test
    fun `validatePassword returns PASSWORD_TOO_SHORT when password is less than 8 characters`() {
        val result = InputValidation.validatePassword("AB1!")
        assertEquals(ValidationError.PASSWORD_TOO_SHORT, result)
    }

    @Test
    fun `validateRepeatPassword returns PASSWORD_DO_NOT_MATCH when passwords differ`() {
        val result = InputValidation.validateRepeatPassword(
            "Abcdef1",
            "Abcdef1@"
        )
        assertEquals(ValidationError.PASSWORDS_DO_NOT_MATCH, result)
    }



}