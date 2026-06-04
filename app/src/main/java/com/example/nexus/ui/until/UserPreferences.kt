package com.example.nexus.ui.until

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferences(private val context: Context) {
    private val cryptoHelper = CryptoHelper(context)

    private val dataStore = context.dataStore

    companion object {
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    suspend fun getSavedEmail(): String {
        val preferences = context.dataStore.data.first()
        return preferences[USER_EMAIL] ?: ""
    }

    suspend fun getSavedPassword(): String {
        val encryptedPassword = cryptoHelper.decryptData("user_password")
        return encryptedPassword
    }

    suspend fun getRememberMe(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[REMEMBER_ME] ?: false
    }

    suspend fun savedUserCredentials(email: String, password: String) {
        try {
            val protectedPassword = cryptoHelper.encryptData(password)

            // Salva os dados no DataStore
            context.dataStore.edit { prefs ->
                prefs[USER_EMAIL] = email
                prefs[REMEMBER_ME] = true
            }

            cryptoHelper.saveEncryptedPassword("user_password", protectedPassword)

        } catch (e: Exception) {
            Log.e("UserPreferences", "Erro ao salvar credenciais do usuário", e)
        }
    }

    suspend fun clearUserCredentials() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_EMAIL)
            prefs.remove(REMEMBER_ME)
        }
        cryptoHelper.clearEncryptedPassword("user_password")

    }
}