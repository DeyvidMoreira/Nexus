package com.example.nexus.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.R
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.TextCustom

@Composable
fun IconTextButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = NeonGreen
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        TextCustom(text = text, fontSize = 22.sp)


    }


}

@Composable
@Preview(showBackground = true)
fun IconTextButtonPreview() {
    IconTextButton(
        text = "Save",
        icon = ImageVector.vectorResource(id = R.drawable.ic_save),
        onClick = {}

    )
}

@Composable
@Preview(showBackground = true)
fun IconTextButtonHistoricPreview() {
    IconTextButton(
        text = "Historic",
        icon = ImageVector.vectorResource(id = R.drawable.ic_historic),
        onClick = {}

    )
}