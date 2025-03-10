package com.example.nexus.ui.theme.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.nexus.ui.theme.NeonGreen

@Composable
fun TextCustom(
    text: String,
    fontSize: TextUnit = 12.sp,
    fontFamily: FontFamily = FontFamily.Monospace,
    color: Color = NeonGreen,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = TextStyle(color = NeonGreen),
    modifier: Modifier = Modifier

) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = fontFamily,
        color = color,
        fontWeight = fontWeight,
        style = style,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TextCustomPreview(){
    TextCustom(text = "Preview")
}