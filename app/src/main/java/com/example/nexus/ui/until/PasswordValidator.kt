package com.example.nexus.ui.until

import com.example.nexus.framework.common.constants.ConstantsMessages

object  PasswordValidator {
    fun validate(tag: String, password: String) : String?{

        return when {
            tag.isEmpty() -> ConstantsMessages.MESSAGE_NO_TAG
            password.isEmpty() -> ConstantsMessages.MESSAGE_NO_GENERATE_PASSWORD
            else -> null

        }

    }
}