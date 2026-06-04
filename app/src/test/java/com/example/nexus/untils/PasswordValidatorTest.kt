package com.example.nexus.untils

import com.example.nexus.ui.until.PasswordValidator
import com.example.pwdcripto.framework.contants.ConstantsMessages
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class PasswordValidatorTest {

    @Test
    fun `validate returns no tag message when tag is empty`() {
        val result = PasswordValidator.validate("", "generated-password")

        assertEquals(ConstantsMessages.MESSAGE_NO_TAG, result)
    }

    @Test
    fun `validate returns no generated password message when password is empty`() {
        val result = PasswordValidator.validate("email", "")

        assertEquals(ConstantsMessages.MESSAGE_NO_GENERATE_PASSWORD, result)
    }

    @Test
    fun `validate returns null when tag and password are filled`() {
        val result = PasswordValidator.validate("email", "generated-password")

        assertNull(result)
    }
}
