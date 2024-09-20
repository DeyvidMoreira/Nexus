package com.example.nexus.ui.theme.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.example.nexus.ui.components.NavigationGraph
import com.example.nexus.ui.components.TextButtonCustom
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom

@Composable
fun SingInScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMeCheck by remember { mutableStateOf(false) }

    ColumnBackgroundColor {
        TextCustom(
            text = stringResource(id = R.string.app_name),
            fontSize = 36.sp,
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

        SpacerCustom(paddingBottom = 50.dp)

        AnimatedBorderCard(
            modifier = Modifier
                .width(300.dp)
                .padding(all = 2.dp)
                .height(400.dp),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            borderGradient = Brush.sweepGradient(listOf(LightGreen, NeonGreen)),
            animationDuration = 5000
        ) {
            Column(
                modifier = Modifier.padding(all = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Username
                TextFieldCustom(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    hint = stringResource(id = R.string.hint_username),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                )
                // Password
                TextFieldCustom(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    hint = stringResource(id = R.string.hint_password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    icon = R.drawable.ic_pwd
                )

                SpacerCustom(paddingBottom = 20.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 6.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMeCheck,
                        onCheckedChange = {
                            rememberMeCheck = !rememberMeCheck
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = NeonGreen,
                            uncheckedColor = NeonGreen,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    SpacerCustom(paddingEnd = 6.dp)
                    TextButtonCustom(onClick = {
                        rememberMeCheck = rememberMeCheck != true
                    }) {
                        TextCustom(
                            text = stringResource(id = R.string.remember_me),
                            fontSize = 14.sp
                        )
                    }

                    SpacerCustom(paddingStart = 10.dp, paddingEnd = 10.dp)

                    TextButtonCustom(onClick = {
                        navController.navigate(NavigationGraph.FORGOT_PASSWORD)
                    }) {
                        TextCustom(
                            text = stringResource(id = R.string.forgot_password),
                            fontSize = 14.sp
                        )
                    }
                }

                SpacerCustom(paddingBottom = 30.dp)

                ButtomCustom(
                    onClick = {
                        println("botao clicado")
                    }
                ) {
                    Text(text = stringResource(id = R.string.btn_login))
                }

                SpacerCustom(paddingBottom = 25.dp)

                TextButtonCustom(onClick = { navController.navigate(NavigationGraph.REGISTER) }) {
                    TextCustom(
                        text = stringResource(id = R.string.register),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun SingInScreenPreview() {
    //Mock do navController para renderizar o preview
    val navController = rememberNavController()
    SingInScreen(navController = navController)
}