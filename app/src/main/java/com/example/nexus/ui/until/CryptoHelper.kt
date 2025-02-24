package com.example.nexus.ui.until

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class CryptoHelper(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun encryptData(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }

    fun decryptData(key: String): String {
        return encryptedPrefs.getString(key, "") ?: ""
    }

}