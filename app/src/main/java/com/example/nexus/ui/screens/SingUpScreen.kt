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
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.navigation.AuthNavigationGraph
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.MatteGreen
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom
/**
 * Função composable para a tela de cadastro.
 * Permite que o usuário crie uma nova conta fornecendo as informações necessárias.
 *
 * @param navController Controlador de navegação para gerenciar a navegação do aplicativo.
 */
@Composable
fun SingUpScreen(navController: NavController) {

    // Variáveis de estado para armazenar a entrada do usuário
    var username by rememberSaveable { mutableStateOf("") }
    var userEmail by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }

    ColumnBackgroundColor {

        // Título com estilo gradiente
        Text(
            text = stringResource(id = R.string.txt_account_create),
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

        SpacerCustom(paddingBottom = 50.dp)

        // Cartão com borda animada contendo o formulário de cadastro
        AnimatedBorderCard(
            modifier = Modifier
                .width(300.dp)
                .padding(all = 2.dp)
                .height(480.dp),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            borderGradient = Brush.sweepGradient(listOf(LightGreen, NeonGreen)),
            animationDuration = 5000
        ) {
            // Conteúdo do formulário
            Column(
                modifier = Modifier.padding(all = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Texto "Sign Up"
                TextCustom(
                    text = stringResource(id = R.string.txt_sing_up),
                    fontSize = 20.sp
                )

                SpacerCustom(paddingBottom = 10.dp)

                // Campo de entrada para o nome de usuário
                TextFieldCustom(
                    value = username,
                    onValueChange = { username = it },
                    hint = stringResource(id = R.string.hint_first_name),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                SpacerCustom(paddingBottom = 8.dp)

                // Campo de entrada para o email do usuário
                TextFieldCustom(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    hint = stringResource(id = R.string.hint_user_email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    icon = R.drawable.icon_email
                )

                SpacerCustom(paddingBottom = 8.dp)

                // Campo de entrada para a senha
                TextFieldCustom(
                    value = password,
                    onValueChange = { password = it },
                    hint = stringResource(id = R.string.hint_password_register),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    icon = R.drawable.ic_pwd,
                    visualTransformation = PasswordVisualTransformation()
                )

                SpacerCustom(paddingBottom = 8.dp)

                // Campo de entrada para repetir a senha
                TextFieldCustom(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    hint = stringResource(id = R.string.hint_repeat_password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    icon = R.drawable.ic_pwd,
                    visualTransformation = PasswordVisualTransformation()
                )

                SpacerCustom(paddingBottom = 20.dp)

                // Botão para criar nova conta
                ButtomCustom(
                    onClick = { navController.navigate(AuthNavigationGraph.SING_IN) }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_new_account),
                        color = DarkGrey
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SingUpScreenScreenPreview() {
    val navController = rememberNavController()
    SingUpScreen(navController)
}



