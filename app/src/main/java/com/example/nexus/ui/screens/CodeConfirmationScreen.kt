package com.example.nexus.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom

@Composable
fun CodeConfirmationScreen(navController: NavController) {

    var code by remember { mutableStateOf("") }

    ColumnBackgroundColor {
        TextCustom(
            text = "",
            fontSize = 22.sp
        )
        SpacerCustom(
            paddingBottom = 50.dp
        )
        AnimatedBorderCard(
            modifier = Modifier
                .width(300.dp)
                .padding(all = 2.dp)
                .height(450.dp),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            borderGradient = Brush.sweepGradient(listOf(LightGreen, NeonGreen)),
            animationDuration = 5000
        ) {
            Column(
                modifier = Modifier.padding(all = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextCustom(
                    text = "",
                    fontSize = 18.sp
                )
                SpacerCustom(
                    paddingBottom = 30.dp
                )
                TextFieldCustom(
                    value = code,
                    onValueChange = {
                        code = it
                    },
                    hint = stringResource(id = R.string.hint_send_code),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    icon = R.drawable.icon_code
                )
                SpacerCustom(
                    paddingBottom = 30.dp
                )
                ButtomCustom(
                    onClick = {
                        println("botao clicado")
                    }
                ) {
                    Text(text = stringResource(id = R.string.btn_confirm_code))
                }


            }
        }
    }

}

@Preview
@Composable
private fun CodeConfirmationPreview() {
    val navController = rememberNavController()
    CodeConfirmationScreen(navController)
}