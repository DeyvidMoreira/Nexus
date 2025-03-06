package com.example.nexus.ui.until

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import android.util.Base64

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

    fun encryptData(value: String): String {
        // Criptografa a senha e retorna a versão codificada em Base64
        val encryptedValue = value.toByteArray(Charsets.UTF_8)
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    fun decryptData(key: String): String {
        // Recupera e descriptografa a senha
        val encryptedValue = encryptedPrefs.getString(key, "") ?: ""
        val decodedBytes = Base64.decode(encryptedValue, Base64.DEFAULT)
        return String(decodedBytes, Charsets.UTF_8)
    }

    fun saveEncryptedPassword(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }

    fun clearEncryptedPassword(key: String) {
        encryptedPrefs.edit().remove(key).apply()
    }
}