package com.example.nexus.ui.components.texts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.NeonGreen


@Composable
fun CustomMessageBox(message: String, isSuccess: Boolean) {

    val backgroundColor = if (isSuccess) NeonGreen else Color.Red
    val textColor = if (isSuccess) DarkGrey else Color.White
    val icon = if (isSuccess) Icons.Default.Check else Icons.Default.Error

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = message,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

        }
    }
}

@Composable
@Preview
fun CustomMessageBoxPreview() {
    CustomMessageBox(message = "Sucesso", isSuccess = false)
}

@Composable
@Preview
fun CustomMessageBoxPositivePreview() {
    CustomMessageBox(message = "Sucesso", isSuccess = true)
}