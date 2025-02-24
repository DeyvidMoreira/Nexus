package com.example.nexus.ui.theme.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.nexus.framework.service.remote.entity.UserModel
import com.example.nexus.ui.components.CustomMessageBox
import com.example.nexus.ui.components.TextButtonCustom
import com.example.nexus.ui.states.SignInState
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

@Composable
fun SingInScreen(
    uiState: SignInState,
    onEnterClick: (UserModel) -> Unit,
    onNavigationToSignUp: () -> Unit = {},
    onNavigationToForgotPassword: () -> Unit = {}
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    val warningMessage by rememberSaveable { mutableStateOf<String?>(null) }
    val currentWarningMessage = warningMessage ?: uiState.warningMessage

    ColumnBackgroundColor {
        currentWarningMessage?.let { message ->
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                CustomMessageBox(
                    message = message,
                    isSuccess = uiState.isSuccessful
                )
            }
        }

        SpacerCustom(paddingBottom = 36.dp)

        // Nome do aplicativo com estilo gradiente
        TextCustom(
            text = stringResource(id = R.string.app_name),
            fontSize = 36.sp,
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

        // Cartão com borda animada contendo o formulário de login
        AnimatedBorderCard(
            modifier = Modifier
                .width(300.dp)
                .padding(all = 2.dp)
                .height(400.dp),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            borderGradient = Brush.sweepGradient(listOf(LightGreen, NeonGreen)),
            animationDuration = 5000,

        ) {
            // Conteúdo do formulário
            Column(
                modifier = Modifier.padding(all = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Campo de entrada para o email
                TextFieldCustom(
                    value = uiState.email,
                    onValueChange = { newEmail -> uiState.onEmailChange(newEmail) },
                    hint = stringResource(id = R.string.hint_user_email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                // Campo de entrada para a senha com opção de mostrar/ocultar
                TextFieldCustom(
                    value = uiState.password,
                    onValueChange = { newPassword -> uiState.onPasswordChange(newPassword) },
                    hint = stringResource(id = R.string.hint_password),
                    icon = R.drawable.ic_pwd,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    showTrailingIcon = true,
                    onTrailingIconClick = { passwordVisibility = !passwordVisibility },
                    trailingIcon = if (passwordVisibility) R.drawable.icon_visibility else R.drawable.icon_visibility_off,
                    iconContentDescripition = "Alternar visibilidade da senha"
                )

                SpacerCustom(paddingBottom = 20.dp)

                // Linha contendo checkbox "Lembrar-me" e botão "Esqueceu a senha?"
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 6.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Checkbox "Lembrar-me"
                    Checkbox(
                        checked = uiState.isRememberMeChecked,
                        onCheckedChange = { uiState.onRememberMeClick() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = NeonGreen,
                            uncheckedColor = NeonGreen,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    SpacerCustom(paddingEnd = 6.dp)

                    // Texto "Lembrar-me"
                    TextButtonCustom(onClick = { uiState.onRememberMeClick() }) {
                        TextCustom(
                            text = stringResource(id = R.string.txt_remember_me),
                            fontSize = 14.sp
                        )
                    }

                    SpacerCustom(paddingStart = 10.dp, paddingEnd = 10.dp)

                    // Botão "Esqueceu a senha?"
                    TextButtonCustom(onClick =onNavigationToForgotPassword) {
                        TextCustom(
                            text = stringResource(id = R.string.btn_forgot_password),
                            fontSize = 14.sp
                        )
                    }
                }

                SpacerCustom(paddingBottom = 30.dp)

                // Botão de entrar
                ButtomCustom(
                    onClick = {
                        onEnterClick(UserModel(uiState.email, uiState.password))
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_sing_in),
                        color = DarkGrey
                    )
                }

                SpacerCustom(paddingBottom = 25.dp)

                // Botão para navegar para a tela de cadastro
                TextButtonCustom(onClick = onNavigationToSignUp) {
                    TextCustom(
                        text = stringResource(id = R.string.btn_register),
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
    val uiState = SignInState()
    SingInScreen (onEnterClick = {}, uiState = uiState)
}

@Composable
@Preview("With Error")
private fun ErrorPreview(){
    val uiState = SignInState(warningMessage = "Erro ao carregar dados")
    SingInScreen(uiState, onEnterClick = {})

}