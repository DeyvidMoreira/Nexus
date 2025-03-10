package com.example.nexus.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MediumGrey

@Composable
fun ColumnBackgroundColor(
    horizontal: Alignment.Horizontal,
    vertical: Arrangement.Vertical,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.sweepGradient(
                    listOf(
                        MediumGrey,
                        DarkMediumGrey,
                        MediumGrey
                    )
                )
            ),
        horizontalAlignment = horizontal,
        verticalArrangement = vertical
    ) {
        content()
    }
}

@Composable
@Preview
private fun ColumnBackgroundColor() {

}