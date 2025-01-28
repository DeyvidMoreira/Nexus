package com.example.nexus.ui.components

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.LightGrey
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen

@Composable
fun CustomSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = LightGreen,
            checkedTrackColor = NeonGreen,
            uncheckedThumbColor = LightGrey,
            uncheckedTrackColor = MatteGreen
        )
    )

}

@Preview
@Composable
private fun CustomSwitchPreview() {
    var checked by remember { mutableStateOf(true) }
    CustomSwitch(checked = checked, onCheckedChange = { checked = it })
}