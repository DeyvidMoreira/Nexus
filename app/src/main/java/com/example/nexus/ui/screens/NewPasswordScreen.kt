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
import com.example.nexus.core.service.repository.local.validationFields.InputValidation
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
 * Função composable para a tela de nova senha.
 * Permite que o usuário redefina sua senha.
 *
 * @param navController Controlador de navegação para gerenciar a navegação do aplicativo.
 */
@Composable
fun NewPassowordScreen(navController: NavController) {
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }

    ColumnBackgroundColor {

        // Título com estilo gradiente
        Text(
            text = stringResource(id = R.string.txt_new_password_title),
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.horizontalGradient(
                    listOf(LightGreen, NeonGreen, MatteGreen)
                )
            )
        )

        SpacerCustom(paddingBottom = 50.dp)

        // Cartão com borda animada contendo o formulário de nova senha
        AnimatedBorderCard(
            modifier = Modifier
                .width(300.dp)
                .padding(all = 2.dp)
                .height(450.dp),
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
                // Texto de instrução
                TextCustom(
                    text = stringResource(id = R.string.txt_new_password),
                    fontSize = 20.sp
                )

                SpacerCustom(paddingBottom = 15.dp)

                // Campo de entrada para a nova senha
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

                SpacerCustom(paddingBottom = 15.dp)

                // Campo de entrada para repetir a nova senha
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

                SpacerCustom(paddingBottom = 50.dp)

                // Botão para redefinir a senha
                ButtomCustom(
                    onClick = {
                        InputValidation.validatePassword(password)
                        InputValidation.validateRepeatPassword(password, repeatPassword)
                        // Lógica adicional para redefinir a senha
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.bnt_refresh_password),
                        color = DarkGrey
                    )
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
