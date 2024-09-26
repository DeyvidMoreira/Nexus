package com.example.nexus.ui.theme.screens


import android.annotation.SuppressLint
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.core.service.model.UserModel
import com.example.nexus.ui.components.TextButtonCustom
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
 * Função composable para a tela de login.
 * Permite que o usuário entre no aplicativo fornecendo email e senha.
 *
 * @param navController Controlador de navegação para gerenciar a navegação do aplicativo.
 * @param onEnterClick Função de callback executada quando o usuário clica no botão de entrar.
 */
@SuppressLint("RememberReturnType")
@Composable
fun SingInScreen(navController: NavController, onEnterClick: (UserModel) -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var rememberMeCheck by rememberSaveable { mutableStateOf(false) }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    ColumnBackgroundColor {
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
                    value = email,
                    onValueChange = { email = it },
                    hint = stringResource(id = R.string.hint_user_email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )

                // Campo de entrada para a senha com opção de mostrar/ocultar
                TextFieldCustom(
                    value = password,
                    onValueChange = { password = it },
                    hint = stringResource(id = R.string.hint_password),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
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
                        checked = rememberMeCheck,
                        onCheckedChange = { rememberMeCheck = !rememberMeCheck },
                        colors = CheckboxDefaults.colors(
                            checkedColor = NeonGreen,
                            uncheckedColor = NeonGreen,
                            checkmarkColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    SpacerCustom(paddingEnd = 6.dp)

                    // Texto "Lembrar-me"
                    TextButtonCustom(onClick = { rememberMeCheck = !rememberMeCheck }) {
                        TextCustom(
                            text = stringResource(id = R.string.txt_remember_me),
                            fontSize = 14.sp
                        )
                    }

                    SpacerCustom(paddingStart = 10.dp, paddingEnd = 10.dp)

                    // Botão "Esqueceu a senha?"
                    TextButtonCustom(onClick = { navController.navigate(AuthNavigationGraph.FORGOT_PASSWORD) }) {
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
                        onEnterClick(UserModel(email, password))
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_sing_in),
                        color = DarkGrey
                    )
                }

                SpacerCustom(paddingBottom = 25.dp)

                // Botão para navegar para a tela de cadastro
                TextButtonCustom(onClick = { navController.navigate(AuthNavigationGraph.SIGN_UP) }) {
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
    //Mock do navController para renderizar o preview
    val navController = rememberNavController()
    SingInScreen(navController = navController, onEnterClick = {})
}