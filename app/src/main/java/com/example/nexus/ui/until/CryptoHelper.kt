package com.example.nexus.ui.until

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import android.util.Base64
import android.util.Log
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CryptoHelper(context: Context) {

    companion object {
        private const val PREFS_NAME = "encrypted_prefs"
        private const val ALIAS = "TOKE_PASSWORD"
        private const val AES_MODE = "AES/GCM/NoPadding"
        private const val IV_SIZE = 12
        private const val KEY_SIZE = 256
    }

    //Master key para SharedPreferences
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    //Criptografar SharedPreferences
    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    //Gerar ou recuperar a chave do KeyStire
    fun getOrCreateSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

        return if (keyStore.containsAlias(ALIAS)) {
            (keyStore.getKey(ALIAS, null) as SecretKey?) ?: throw IllegalStateException("Chave não encontrada")
        } else {
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(KEY_SIZE)
                .build()

            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }


    fun encryptLocalData(value: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(value.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(iv + encryptedBytes, Base64.NO_WRAP)
    }

    fun decryptLocalData(encryptedData: String, secretKey: SecretKey): String {
        if (encryptedData.isEmpty()) throw IllegalArgumentException("Dados criptografados não podem ser vazios")
        val decodedBytes = Base64.decode(encryptedData, Base64.NO_WRAP)
        require(decodedBytes.size >= IV_SIZE) { "Dados criptografados inválidos" }
        val iv = decodedBytes.copyOfRange(0, IV_SIZE)
        val encryptedBytes = decodedBytes.copyOfRange(IV_SIZE, decodedBytes.size)
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encryptedBytes), Charsets.UTF_8)
    }

    //Criptografa SharedPreferences
    fun encryptData(value: String): String {
        // Criptografa a senha e retorna a versão codificada em Base64
        val encryptedValue = value.toByteArray(Charsets.UTF_8)
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    //Descriptografa SharedPreferences
    fun decryptData(key: String): String {
        // Recupera e descriptografa a senha
        val encryptedValue = encryptedPrefs.getString(key, "") ?: ""
        val decodedBytes = Base64.decode(encryptedValue, Base64.DEFAULT)
        return String(decodedBytes, Charsets.UTF_8)
    }

    //Salva SharedPreferences
    fun saveEncryptedPassword(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }

    //Limpa SharedPreferences
    fun clearEncryptedPassword(key: String) {
        encryptedPrefs.edit().remove(key).apply()
    }
}