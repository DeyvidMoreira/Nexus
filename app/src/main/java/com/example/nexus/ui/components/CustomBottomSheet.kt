package com.example.nexus.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel

@Composable
fun PasswordModalBottomSheet(
    viewModel: PwdGeneratorViewModel,
    onDelete: (PasswordEntity) -> Unit
) {
    val passwords by viewModel.passwords.observeAsState(emptyList())
    val filteredPasswords by viewModel.filteredPasswords.observeAsState(emptyList())
    val searchQuery = remember { mutableStateOf("") }

    val sheetState =
        androidx.compose.material.rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(filteredPasswords) {
        if (filteredPasswords.isNotEmpty()) {
            sheetState.show()  // Garante que o BottomSheet seja mostrado quando a lista for carregada
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // Barra de pesquisa
            TextField(
                value = searchQuery.value,
                onValueChange = { query ->
                    searchQuery.value = query
                    viewModel.getPasswordsByTag(query) // Atualiza a consulta de pesquisa no ViewModel
                },
                label = { Text("Pesquisar por Tag") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Lista filtrada de senhas
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .height(300.dp)
                    .padding(0.dp)  // Remover qualquer padding extra que possa criar um espaço indesejado
            ) {
                items(filteredPasswords) { password ->
                    PasswordItem(
                        password = password,
                        onDelete = { onDelete(password) }
                    )
                }
            }
        }
    }
}