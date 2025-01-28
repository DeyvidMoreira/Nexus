package com.example.nexus.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel

@Composable
fun SavePasswordDialog(
    viewModel: PwdGeneratorViewModel,
    onDismiss: () -> Unit,
) {
    val uiState by viewModel.state.collectAsState()

    var passwordTag by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (passwordTag.isNotEmpty()) {
                        viewModel.savePassword(passwordTag, uiState.generatedPassword ?: "")
                        onDismiss()
                    }
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text("Salvar Senha")
        },
        text = {
            Column {
                // Exibe a senha gerada
                Text(
                    text = "Senha: ${uiState.generatedPassword ?: "Nenhuma senha gerada"}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Campo para o usuário inserir o Tag
                OutlinedTextField(
                    value = passwordTag,
                    onValueChange = { passwordTag = it },
                    label = { Text("Tag da Senha") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
