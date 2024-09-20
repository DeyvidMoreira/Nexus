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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom

@Composable
fun NewPassowordScreen(navController: NavController){
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    ColumnBackgroundColor {

        Text(
            text = stringResource(id = R.string.new_password_title),
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    listOf(
                        LightGreen,
                        NeonGreen,
                        MatteGreen
                    )
                )
            )
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
        ){
            Column(
                modifier = Modifier.padding(all = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextCustom(
                    text = stringResource(id = R.string.txt_new_password,),
                    fontSize = 20.sp
                )
                SpacerCustom(
                    paddingBottom = 15.dp
                )
                TextFieldCustom(
                    value = password,
                    onValueChange ={
                        password = it
                    },
                    hint = stringResource(id = R.string.hint_password_register),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    icon = R.drawable.ic_pwd
                )
                SpacerCustom(
                    paddingBottom = 15.dp
                )
                TextFieldCustom(
                    value = repeatPassword,
                    onValueChange ={
                        repeatPassword = it
                    },
                    hint = stringResource(id = R.string.hint_repeat_password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    icon = R.drawable.ic_pwd
                )
                SpacerCustom(
                    paddingBottom = 50.dp
                )
                ButtomCustom(
                    onClick = {
                        println("botao clicado")
                    }
                ) {
                    Text(text = stringResource(id = R.string.bnt_refresh_password))
                }
            }
        }


    }
}

@Preview
@Composable
private fun NewPassowrdScreenPreview() {
    val navController = rememberNavController()
    NewPassowordScreen(navController)
}
