package com.example.nexus.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.R
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SavePasswordDialog(
    viewModel: PwdGeneratorViewModel,
    onDismiss: () -> Unit,
    backgroundColor: Color = DarkGrey,
    uiState: GeneratorState
) {

    var passwordTag by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            ButtomCustom(
                onClick = {
                    if (passwordTag.isNotEmpty()) {
                        viewModel.savePassword(passwordTag, uiState.generatedPassword ?: "")
                        uiState.isPasswordSaved = true
                        onDismiss()
                    }
                }
            ) {
                TextCustom("Salvar", color = DarkGrey)
            }
        },
        dismissButton = {
            ButtomCustom(onClick = onDismiss) {
                TextCustom(text = "Cancelar", color = DarkGrey)
            }
        },
        title = {
            TextCustom("Salvar Senha")
        },
        text = {
            Column {
                // Exibe a senha gerada
                TextCustom(
                    text = "Senha: ${uiState.generatedPassword ?: "Nenhuma senha gerada"}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Usa o TextFieldCustom no lugar do OutlinedTextField
                TextFieldCustom(
                    value = passwordTag,
                    onValueChange = { passwordTag = it },
                    icon = R.drawable.ic_save,
                    hint = "Tag para a Senha",
                    modifier = Modifier.fillMaxWidth(),

                    )
            }
        },
        containerColor = backgroundColor,
        shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun SavePasswordDialogPreview() {
}