package com.example.nexus.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.R
import com.example.nexus.ui.states.SignUpUiState
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
 */
@Composable
fun SignUpScreen(uiState: SignUpUiState, onSignUpClick: () -> Unit) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    ColumnBackgroundColor {
        //Mensagem de erro de registro
        AnimatedVisibility(visible = uiState.error != null) {
            uiState.error?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Red)
                ) {
                    Text(
                        text = it,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
        SpacerCustom(paddingBottom = 16.dp)

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
                    value = uiState.user,
                    onValueChange = uiState.onUserChange,
                    hint = stringResource(id = R.string.hint_first_name),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                SpacerCustom(paddingBottom = 8.dp)

                // Campo de entrada para o email do usuário
                TextFieldCustom(
                    value = uiState.email,
                    onValueChange = uiState.onEmailChange,
                    hint = stringResource(id = R.string.hint_user_email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    icon = R.drawable.icon_email
                )
                SpacerCustom(paddingBottom = 8.dp)

                // Campo de entrada para a senha
                TextFieldCustom(
                    value = uiState.password,
                    onValueChange = uiState.onPasswordChange,
                    hint = stringResource(id = R.string.hint_password_register),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    icon = R.drawable.ic_pwd,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    showTrailingIcon = true,
                    onTrailingIconClick = {
                        passwordVisibility = !passwordVisibility
                    },
                    trailingIcon = if (passwordVisibility) R.drawable.icon_visibility
                    else R.drawable.icon_visibility_off,
                    iconContentDescripition = "Alternar visibilidade da senha"
                )
                SpacerCustom(paddingBottom = 8.dp)

                // Campo de entrada para repetir a senha
                TextFieldCustom(
                    value = uiState.confirmPassword,
                    onValueChange = uiState.onConfirmPasswordChange,
                    hint = stringResource(id = R.string.hint_repeat_password),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    icon = R.drawable.ic_pwd,
                    iconContentDescripition = "Alternar visibilidade da senha"
                )
                SpacerCustom(paddingBottom = 20.dp)

                // Botão para criar nova conta
                ButtomCustom(
                    onClick = onSignUpClick
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

@Preview(name = "Default")
@Composable
private fun SingUpScreenPreview() {
    val uiState = SignUpUiState()
    SignUpScreen(uiState) {}
}


@Preview(name = "With Error")
@Composable
private fun SingUpScreen1Preview() {
    SignUpScreen(
        uiState = SignUpUiState(error = "Error")
    ) {}
}

