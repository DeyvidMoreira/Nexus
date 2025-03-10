package com.example.nexus.ui.components.itens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.framework.service.local.until.toFormattedDate
import com.example.nexus.framework.service.local.until.toFormattedTime
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.dialogs.DeleteDialog
import com.example.nexus.ui.components.dialogs.EditPasswordDialog
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PasswordItem(
    password: PasswordEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    viewModel: PwdGeneratorViewModel
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var passwordToEdit by remember { mutableStateOf<PasswordEntity?>(null) }
    var swipeToDismissEnabled by remember { mutableStateOf(true) }

    val dismissState = rememberDismissState(
        confirmStateChange = {
            when (it) {
                DismissValue.DismissedToStart -> {
                    showEditDialog = true
                    swipeToDismissEnabled = false
                    true
                }

                DismissValue.DismissedToEnd -> {
                    showDeleteDialog = true
                    swipeToDismissEnabled = false
                    true
                }

                else -> false
            }
        }
    )

    //Resetar o dismiss
    LaunchedEffect (showEditDialog, showDeleteDialog){
        if (showEditDialog || showDeleteDialog) {
            dismissState.reset()
        }
    }

    //Exibe o dialog
    if (showDeleteDialog) {
        DeleteDialog(
            onDismiss = {
                showDeleteDialog = false
                swipeToDismissEnabled = true
            },
            backgroundColor = DarkGrey,
            viewModel = viewModel,
            password = password
        )
    }
    if (showEditDialog) {
        EditPasswordDialog(
            onDismiss = {
                showEditDialog = false
                swipeToDismissEnabled = true
            },
            backgroundColor = DarkGrey,
            viewModel = viewModel,
            passwordToEdit = passwordToEdit ?: password
        )
    }
    if (swipeToDismissEnabled) {
        SwipeToDismiss(
            state = dismissState,
            directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
            background = {
                val color = when (dismissState.targetValue) {
                    DismissValue.DismissedToEnd -> Color.Red
                    DismissValue.DismissedToStart -> NeonGreen
                    else -> DarkGrey
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    if (dismissState.targetValue == DismissValue.DismissedToEnd) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar Tag",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                }
            },
            dismissContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = DarkGrey,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        TextCustom(
                            text = password.tag,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        TextCustom(
                            text = password.password,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            TextCustom(
                                text = "Data: ${password.createdAt.toFormattedDate()}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            SpacerCustom(paddingEnd = 32.dp)
                            TextCustom(
                                text = "Hora: ${password.createdAt.toFormattedTime()}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(
                    color = DarkGrey,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextCustom(
                    text = password.tag,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                TextCustom(
                    text = password.password,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    TextCustom(
                        text = "Data: ${password.createdAt.toFormattedDate()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    SpacerCustom(paddingEnd = 32.dp)
                    TextCustom(
                        text = "Hora: ${password.createdAt.toFormattedTime()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PasswordItemPreview() {

}