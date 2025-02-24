package com.example.nexus.ui.until

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferences(private val context: Context) {
    private val cryptoHelper = CryptoHelper(context)

    companion object {
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_PASSWORD = stringPreferencesKey("user_password")
        private val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    suspend fun getSavedEmail(): String {
        val preferences = context.dataStore.data.first()
        return preferences[USER_EMAIL] ?: ""
    }

    suspend fun getSavedPassword(): String {
        val preferences = context.dataStore.data.first()
        val encryptedPassword = preferences[USER_PASSWORD] ?: ""
        return cryptoHelper.decryptData(encryptedPassword)
    }

    suspend fun getRememberMe(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[REMEMBER_ME] ?: false
    }

    suspend fun savedUserCredentials(email: String, password:String){
        val encryptedPassword = cryptoHelper.encryptData("password",password)
        context.dataStore.edit { prefs ->
            prefs[USER_EMAIL] = email
            prefs[USER_PASSWORD] = encryptedPassword.toString()
            prefs[REMEMBER_ME] = true
        }
    }

    suspend fun clearUserCredentials() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_EMAIL)
            prefs.remove(USER_PASSWORD)
            prefs[REMEMBER_ME] = false
        }
    }
}