package com.example.nexus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.LightGrey
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen

@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
){
    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,
        enabled = true,
        colors = SliderDefaults.colors(
            thumbColor = MatteGreen,
            activeTrackColor = NeonGreen,
            inactiveTrackColor = DarkGrey
        ),
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
@Preview
fun CustomSliderPreview(){
    CustomSlider(value = 0.5f, onValueChange = {})

}