package com.example.nexus.untils

import com.example.nexus.framework.common.validation.ValidationError
import com.example.nexus.framework.common.validation.InputValidation
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class InputValidationComprehensiveTest {

    @Test
    fun `validateName rejects blank names`() {
        assertThat(InputValidation.validateName("")).isEqualTo(ValidationError.INVALID_NAME)
        assertThat(InputValidation.validateName("   ")).isEqualTo(ValidationError.INVALID_NAME)
    }

    @Test
    fun `validateName rejects leading or trailing spaces`() {
        assertThat(InputValidation.validateName(" Maria")).isEqualTo(ValidationError.INVALID_NAME)
        assertThat(InputValidation.validateName("Maria ")).isEqualTo(ValidationError.INVALID_NAME)
    }

    @Test
    fun `validateName rejects numbers and symbols`() {
        assertThat(InputValidation.validateName("Maria1")).isEqualTo(ValidationError.INVALID_NAME)
        assertThat(InputValidation.validateName("Maria!")).isEqualTo(ValidationError.INVALID_NAME)
    }

    @Test
    fun `validateName accepts alphabetic names with spaces`() {
        assertThat(InputValidation.validateName("Maria Silva")).isNull()
    }

    @Test
    fun `validateEmail rejects malformed emails`() {
        assertThat(InputValidation.validateEmail("usuario")).isEqualTo(ValidationError.INVALID_EMAIL)
        assertThat(InputValidation.validateEmail("usuario@")).isEqualTo(ValidationError.INVALID_EMAIL)
    }

    @Test
    fun `validateEmail accepts common valid emails`() {
        assertThat(InputValidation.validateEmail("user@domain.com")).isNull()
        assertThat(InputValidation.validateEmail("user.name+tag@domain.com")).isNull()
    }

    @Test
    fun `validatePassword rejects empty password`() {
        assertThat(InputValidation.validatePassword("")).isEqualTo(ValidationError.PASSWORD_EMPTY)
    }

    @Test
    fun `validatePassword rejects short password`() {
        assertThat(InputValidation.validatePassword("Aa1!")).isEqualTo(ValidationError.PASSWORD_TOO_SHORT)
    }

    @Test
    fun `validatePassword rejects password without uppercase`() {
        assertThat(InputValidation.validatePassword("password1!"))
            .isEqualTo(ValidationError.PASSWORD_NO_UPPERCASE)
    }

    @Test
    fun `validatePassword rejects password without lowercase`() {
        assertThat(InputValidation.validatePassword("PASSWORD1!"))
            .isEqualTo(ValidationError.PASSWORD_NO_LOWERCASE)
    }

    @Test
    fun `validatePassword rejects password without number`() {
        assertThat(InputValidation.validatePassword("Password!"))
            .isEqualTo(ValidationError.PASSWORD_NO_NUMBER)
    }

    @Test
    fun `validatePassword rejects password without supported special character`() {
        assertThat(InputValidation.validatePassword("Password1"))
            .isEqualTo(ValidationError.PASSWORD_NO_SPECIAL_CHAR)
    }

    @Test
    fun `validatePassword accepts strong password`() {
        assertThat(InputValidation.validatePassword("Password1!")).isNull()
    }

    @Test
    fun `validateRepeatPassword rejects mismatched passwords`() {
        assertThat(InputValidation.validateRepeatPassword("Password1!", "Password2!"))
            .isEqualTo(ValidationError.PASSWORDS_DO_NOT_MATCH)
    }

    @Test
    fun `validateRepeatPassword accepts matching passwords`() {
        assertThat(InputValidation.validateRepeatPassword("Password1!", "Password1!")).isNull()
    }

    @Test
    fun `validateEmptyFields rejects any blank field`() {
        assertThat(InputValidation.validateEmptyFields("Maria", "", "Password1!"))
            .isEqualTo(ValidationError.EMPTY_FIELDS)
    }

    @Test
    fun `validateEmptyFields accepts filled fields`() {
        assertThat(InputValidation.validateEmptyFields("Maria", "user@domain.com", "Password1!"))
            .isNull()
    }
}
