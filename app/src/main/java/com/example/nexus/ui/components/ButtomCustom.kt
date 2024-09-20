package com.example.nexus.ui.theme.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.NeonGreen

@Composable
fun ButtomCustom(
    onClick: () -> Unit = {}, content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = NeonGreen, contentColor = LightGreen
        ),
        shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp),
    ) {
        content()
    }
}

@Preview
@Composable
private fun PreviewButtomCustom() {
    ButtomCustom(onClick = { println("Botão clicado") }) {
        Text(text = "Botão ")
    }
}