package com.example.nexus.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.framework.service.local.until.toFormattedDate
import com.example.nexus.framework.service.local.until.toFormattedTime
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom

@Composable
fun PasswordItem(
    password: PasswordEntity,
    onDelete: (PasswordEntity) -> Unit
) {
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
        ) {
            TextCustom(
                text = password.tag,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            TextCustom(
                text = password.password,
                style = MaterialTheme.typography.bodyMedium
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
        IconButton(onClick = { onDelete(password) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Excluir senha",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview
@Composable
fun PasswordItemPreview(){
    val password = PasswordEntity(
        id = 1,
        tag = "Exemplo",
        password = "senha123",
        createdAt = System.currentTimeMillis()
    )
    PasswordItem(password = password, onDelete = {})

}