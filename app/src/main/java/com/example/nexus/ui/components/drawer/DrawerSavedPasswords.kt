package com.example.nexus.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.R
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.itens.PasswordItem
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.components.TextFieldCustom

@Composable
fun DrawerSavedPasswords(
    viewModel: PwdGeneratorViewModel,
    onDelete: (PasswordEntity) -> Unit,
){
    val filteredPasswords by viewModel.filteredPasswords.observeAsState(emptyList())
    val searchQuery = remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(DarkMediumGrey),
        verticalArrangement = Arrangement.Top,

        ) {
        item {
            TextFieldCustom(
                value = searchQuery.value,
                onValueChange = { query ->
                    searchQuery.value = query
                    viewModel.getPasswordsByTag(query)
                },
                hint = stringResource(R.string.search_password),
                icon = R.drawable.ic_search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        if (filteredPasswords.isEmpty()) {
            item {
                Text(
                    text = "Nenhuma senha encontrada",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        } else {
            items(filteredPasswords) { password ->
                PasswordItem(
                    password = password,
                    onDelete = { onDelete(password) },
                    onEdit = { viewModel.editPassword(password) },
                    viewModel = viewModel
                )
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun DrawerSavedPasswordsPreview() {
}