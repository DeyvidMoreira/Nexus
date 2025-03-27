package com.example.nexus.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.R
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.TextCustom

@Composable
fun WarningDialog(
    title: String = "",
    message: String = "",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    backgroundColor: Color = DarkGrey,
    viewModel: PwdGeneratorViewModel,
    password: PasswordEntity
) {
    AlertDialog(
        modifier = Modifier
            .padding(16.dp),
        onDismissRequest = onDismiss,
        confirmButton = {
            ButtomCustom(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                TextCustom("Sim", color = DarkGrey)
            }
        },
        dismissButton = {
            ButtomCustom(onClick = onDismiss) {
                TextCustom(text = "Não", color = DarkGrey)
            }
        },
        title = { TextCustom(text = title) },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextCustom(
                    text = message,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            }
        },
        containerColor = backgroundColor,
        shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp)
    )
}


@Composable
@Preview
fun WarningDialogPreview() {
}