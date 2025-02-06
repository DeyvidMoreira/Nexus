package com.example.nexus.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MediumGrey

@Composable
fun ColumnBackgroundColor(content: @Composable () -> Unit){
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        content()
    }
}

@Composable
@Preview
private fun ColumnBackgroundColor() {

}