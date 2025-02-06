package com.example.nexus.ui.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexus.R
import com.example.nexus.ui.components.IconTextButton
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MediumGrey

@Composable
fun DrawerMenuContent(modifier: Modifier = Modifier, onOpenBottomSheet: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.sweepGradient(
                    listOf(
                        MediumGrey,
                        DarkMediumGrey,
                        MediumGrey
                    )
                )
            ) // Mantém a cor preta no conteúdo
            .padding(16.dp)
    ) {
        IconTextButton(
            text = "Historic",
            icon = ImageVector.vectorResource(id = R.drawable.ic_historic),
            onClick = {}
        )

        IconTextButton(
            text = "Save",
            icon = ImageVector.vectorResource(id = R.drawable.ic_save),
            onClick = {
                onOpenBottomSheet()
            }
        )

    }



}
@Composable
@Preview(showBackground = true)
fun DrawerMenuPreview() {
    DrawerMenuContent(onOpenBottomSheet = {})

}