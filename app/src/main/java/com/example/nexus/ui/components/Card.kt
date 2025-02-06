package com.example.nexus.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.theme.DarkGrey

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(size = 0.dp),
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = Color.Black
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = DarkGrey,
            shape = shape
        ) {
            content()
        }
    }
}

@Composable
@Preview
private fun AnimatedBorderCardPreview() {
    CustomCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(2.dp),
        shape = RoundedCornerShape(30.dp)
    ){

    }
}
