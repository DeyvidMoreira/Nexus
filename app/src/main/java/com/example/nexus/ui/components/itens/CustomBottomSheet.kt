package com.example.nexus.ui.components.itens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.R
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.dialogs.DeleteDialog
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom

@Composable
fun PasswordModalBottomSheet(
    viewModel: PwdGeneratorViewModel,
    onDelete: (PasswordEntity) -> Unit,
    onEdit: (PasswordEntity) -> Unit
) {
    val passwords by viewModel.passwords.observeAsState(emptyList())
    val filteredPasswords by viewModel.filteredPasswords.observeAsState(emptyList())
    val searchQuery = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 600.dp)
            .background(DarkGrey)
    ) {

        // Barra de pesquisa
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

        if (showDialog.value) {
            DeleteDialog(
                onDismiss = { showDialog.value = false },
                viewModel = viewModel,
                password = PasswordEntity()
            )
        }


        // Lista filtrada de senhas
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .height(300.dp)
                .padding(0.dp)
        ) {
            if (filteredPasswords.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        SpacerCustom(paddingTop = 24.dp)
                        TextCustom(
                            text = stringResource(id = R.string.no_passwords_found),
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(
                                brush = Brush.horizontalGradient(
                                    listOf(LightGreen, NeonGreen, MatteGreen)
                                )
                            )
                        )
                    }
                }
            } else {
                try {
                    items(filteredPasswords) { password ->
                        PasswordItem(
                            password = password,
                            onDelete = { onDelete(password) },
                            onEdit = { onEdit(password) },
                            viewModel = viewModel
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}


