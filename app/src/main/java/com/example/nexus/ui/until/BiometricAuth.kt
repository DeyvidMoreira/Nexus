package com.example.nexus.ui.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.nexus.R
import com.example.nexus.ui.until.BiometricPromptManager

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("ContextCast", "ContextCastToActivity")
@Composable
fun BiometricAuth(
    modifier: Modifier = Modifier,
    title: String = "Autenticação Biométrica",
    description: String = "Use sua digital para continuar",
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val activity = LocalContext.current as? AppCompatActivity
    val manager = remember(activity) {
        activity?.let { BiometricPromptManager(it) }
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(manager) {
        manager?.promptResult?.collect { result ->
            when (result) {
                is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> onSuccess()
                is BiometricPromptManager.BiometricResult.AuthenticationError -> onError(result.error)
                BiometricPromptManager.BiometricResult.AuthenticationFailed -> onError("Biometria não reconhecida")
                BiometricPromptManager.BiometricResult.FeatureUnavailable -> onError("Recurso indisponível")
                BiometricPromptManager.BiometricResult.HardWareUnavailable -> onError("Hardware indisponível")
                BiometricPromptManager.BiometricResult.AuthenticationNotSet -> onError("Nenhuma biometria cadastrada")
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = {
            if (activity == null) {
                onError("Contexto inválido")
            } else {
                manager?.showBiometricPrompt(title, description)
            }
        }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_fingerprint),
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text("Entrar com Biometria")
    }
}