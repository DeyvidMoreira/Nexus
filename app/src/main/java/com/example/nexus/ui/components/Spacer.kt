package com.example.nexus.ui.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerCustom(
    paddingStart: Dp = 0.dp,
    paddingTop: Dp = 0.dp,
    paddingEnd: Dp = 0.dp,
    paddingBottom: Dp = 0.dp
){
    androidx.compose.foundation.layout.Spacer(
        modifier = Modifier
            .padding(
                start = paddingStart,
                top = paddingTop,
                end = paddingEnd,
                bottom = paddingBottom
            )
    )
}