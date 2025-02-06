package com.example.nexus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nexus.R
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.MediumGrey
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom

@Composable
fun PasswordModalBottomSheet(
    viewModel: PwdGeneratorViewModel,
    onDelete: (PasswordEntity) -> Unit
) {
    val passwords by viewModel.passwords.observeAsState(emptyList())
    val filteredPasswords by viewModel.filteredPasswords.observeAsState(emptyList())
    val searchQuery = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 600.dp)
            .background(DarkGrey)
    ) {

            // Barra1 de pesquisa
            TextField(
                value = searchQuery.value,
                onValueChange = { query ->
                    searchQuery.value = query
                    viewModel.getPasswordsByTag(query) // Atualiza a consulta de pesquisa no ViewModel
                },
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = NeonGreen,
                    unfocusedContainerColor = DarkGrey,
                    focusedTextColor = MatteGreen,
                    focusedContainerColor = DarkGrey,
                    errorTextColor = Color.Red,
                    cursorColor = NeonGreen,
                    errorCursorColor = Color.Red,
                    focusedLabelColor = MatteGreen,
                    unfocusedLabelColor = NeonGreen,
                    errorLabelColor = Color.Red,
                    errorIndicatorColor = Color.Red,
                    focusedIndicatorColor = MatteGreen,
                    unfocusedIndicatorColor = NeonGreen
                ),
                label = { TextCustom("Pesquisar por Tag") },
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


