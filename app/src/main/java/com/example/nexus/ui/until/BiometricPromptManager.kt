package com.example.nexus.ui.until


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow


class BiometricPromptManager(private val context: Context) {

    private val resultChannel = Channel<BiometricResult>()
    val promptResult = resultChannel.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.R)
    fun showBiometricPrompt(
        title: String,
        description: String
    ) {
        if (context !is AppCompatActivity) {
            resultChannel.trySend(
                BiometricResult.AuthenticationError("Contexto não é uma Activity")
            )
            return
        }

        val manager = BiometricManager.from(context)
        val authenticators =
            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL

        val promptInfoBuilder = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticators)

        val promptInfo = promptInfoBuilder.build()

        when (val authResult = manager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> Unit
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(BiometricResult.HardWareUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(BiometricResult.FeatureUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(BiometricResult.AuthenticationNotSet)
                return
            }

            else -> {
                resultChannel.trySend(BiometricResult.AuthenticationError("Erro desconhecido: $authResult"))
                return
            }
        }

        // Criação do BiometricPrompt com Activity
        val biometricPrompt = BiometricPrompt(
            context,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    val errorMessage = when (errorCode) {
                        BiometricPrompt.ERROR_LOCKOUT -> "Bloqueio devido a tentativas incorretas"
                        BiometricPrompt.ERROR_HW_UNAVAILABLE -> "Hardware biométrico indisponível"
                        BiometricPrompt.ERROR_NO_SPACE -> "Espaço insuficiente para autenticação"
                        else -> "Erro desconhecido: $errString"
                    }
                    resultChannel.trySend(BiometricResult.AuthenticationError(errorMessage))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(BiometricResult.AuthenticationFailed)
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }

    sealed interface BiometricResult {
        data object HardWareUnavailable : BiometricResult
        data object FeatureUnavailable : BiometricResult
        data class AuthenticationError(val error: String) : BiometricResult
        data object AuthenticationFailed : BiometricResult
        data object AuthenticationSuccess : BiometricResult
        data object AuthenticationNotSet : BiometricResult
        data object AuthenticationCanceled : BiometricResult  // Novo caso
    }
}
