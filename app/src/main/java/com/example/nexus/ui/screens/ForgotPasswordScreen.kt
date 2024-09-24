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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.core.service.repository.local.validationFields.InputValidation
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom

/**
 * Função composable para a tela de esqueci a senha.
 * Permite que o usuário solicite a redefinição da senha fornecendo o email.
 *
 * @param navController Controlador de navegação para gerenciar a navegação do aplicativo.
 */
@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var userEmail by rememberSaveable { mutableStateOf("") }

    ColumnBackgroundColor {
        // Título
        TextCustom(
            text = stringResource(id = R.string.txt_title_forgot_password),
            fontSize = 22.sp
        )

        SpacerCustom(paddingBottom = 50.dp)

        // Cartão com borda animada contendo o formulário de solicitação
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
                // Mensagem de instrução
                TextCustom(
                    text = stringResource(id = R.string.txt_message_send_email),
                    fontSize = 18.sp
                )

                SpacerCustom(paddingBottom = 30.dp)

                // Campo de entrada para o email
                TextFieldCustom(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    hint = stringResource(id = R.string.hint_email_request),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    icon = R.drawable.icon_email
                )

                SpacerCustom(paddingBottom = 30.dp)

                // Botão para enviar o link de redefinição
                ButtomCustom(
                    onClick = {
                        val error = InputValidation.validateEmail(userEmail)
                        if (error == null) {
                            // Lógica para enviar o email de redefinição
                        } else {
                            // Exibir mensagem de erro
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.btn_send_link),
                        color = DarkGrey
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordPreview() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController)
}