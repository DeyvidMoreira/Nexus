package com.example.nexus.ui.components.itens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.AlertDialog
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.R
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.framework.service.local.until.toFormattedDate
import com.example.nexus.framework.service.local.until.toFormattedTime
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.buttons.TextButtonCustom
import com.example.nexus.ui.components.dialogs.WarningDialog
import com.example.nexus.ui.components.dialogs.EditPasswordDialog
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import kotlinx.coroutines.delay
import com.example.nexus.ui.until.WarningMessage
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PasswordItem(
    password: PasswordEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    viewModel: PwdGeneratorViewModel,
    uiState: GeneratorState
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var passwordToEdit by remember { mutableStateOf<PasswordEntity?>(null) }
    var isPasswordReveled by remember { mutableStateOf(false) }
    var swipeToDismissEnabled by remember { mutableStateOf(true) }
    val clipboardManager = LocalClipboardManager.current

    var showPasswordDialog by remember { mutableStateOf(false) }

    //Dialog de revelar senha
    if (showPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showPasswordDialog = false },
            title = { TextCustom(text = "Revela Senha") },
            text = { TextCustom(text = "Deseja ver a senha?") },
            confirmButton = {
                TextButtonCustom(
                    onClick = {
                        showPasswordDialog = false
                        isPasswordReveled = true
                    }
                ) {
                    TextCustom(text = "Sim")
                }
            },
            dismissButton = {
                TextButtonCustom(
                    onClick = {
                        showPasswordDialog = false
                        isPasswordReveled = false
                    }
                ) {
                    TextCustom(text = "Não")
                }
            },
            backgroundColor = DarkGrey,
            shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp)

        )
    }


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
    LaunchedEffect(showEditDialog, showDeleteDialog) {
        if (showEditDialog || showDeleteDialog) {
            dismissState.reset()
        }
        if (isPasswordReveled) {
            delay(30.seconds)
            isPasswordReveled = false
        }
    }

    //Exibe o dialog
    if (showDeleteDialog) {
        WarningDialog(
            title = stringResource(id = R.string.title_delete_password),
            message = stringResource(id = R.string.message_delete_password),
            onDismiss = {
                showDeleteDialog = false
                swipeToDismissEnabled = true
            },
            backgroundColor = DarkGrey,
            viewModel = viewModel,
            password = password,
            onConfirm = onDelete
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
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showPasswordDialog = true }
                    ) {
                        TextCustom(
                            text = password.tag,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        TextCustom(
                            text = if (isPasswordReveled) viewModel.getDecryptedPassword(password) else "*******",
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

                    IconButton(
                        modifier = Modifier
                            .padding(2.dp),
                        onClick = {
                            if (isPasswordReveled) {
                                val decryptedPassword = viewModel.getDecryptedPassword(password)
                                clipboardManager.setText(AnnotatedString(decryptedPassword))
                                uiState.isPasswordCopied = true
                                WarningMessage.setMessage("Senha copiada com sucesso!")
                            } else {
                                WarningMessage.setMessage("Revela a senha antes de copiar!")
                            }
                        },
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_copy),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = NeonGreen
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun PasswordItemPreview() {
}