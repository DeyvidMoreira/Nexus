package com.example.nexus.ui.until

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.example.nexus.ui.states.GeneratorState
import com.example.pwdcripto.framework.contants.ConstantsMessages

class ClipboardHelper() {

    fun copyClipboard(context: Context, text: String, uiState: GeneratorState) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager

        val clipData = ClipData.newPlainText("senha", text)
        clipboardManager.setPrimaryClip(clipData)
        uiState.isPasswordCopied = true
        WarningMessage.setMessage(
            ConstantsMessages.MESSAGE_PASSWORD_COPIED
        )
    }


}