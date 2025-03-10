package com.example.nexus.ui.components.options

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.LightGrey
import com.example.nexus.ui.theme.NeonGreen

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbColor = if (checked) LightGrey else LightGreen
    val trackColor = if (checked) NeonGreen else LightGrey
    val borderColor = if (checked) LightGreen else NeonGreen

    Box(
        modifier = Modifier
            .width(48.dp)
            .height(24.dp)
            .border(2.dp, borderColor, CircleShape)
            .background(trackColor, CircleShape)
            .clickable { onCheckedChange(!checked) }
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(thumbColor, CircleShape)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }
}

@Preview
@Composable
private fun CustomSwitchPreview() {
    var checked by remember { mutableStateOf(false) }
    CustomSwitch(checked = checked, onCheckedChange = { checked = it })
}

@Preview
@Composable
private fun CustomSwitchTruePreview() {
    var checked by remember { mutableStateOf(true) }
    CustomSwitch(checked = checked, onCheckedChange = { checked = it })
}