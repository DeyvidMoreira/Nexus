package com.example.nexus.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.nexus.framework.common.validation.InputValidation
import com.example.nexus.ui.ViewModels.ForgotPasswordViewModel
import com.example.nexus.ui.components.texts.CustomMessageBox
import com.example.nexus.ui.states.ResetPasswordState
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.LightGreen
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.theme.components.TextFieldCustom
import com.example.nexus.ui.until.WarningMessage
import org.koin.androidx.compose.getViewModel


@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = getViewModel()
) {
    val state by viewModel.resetState.collectAsState()
    val warningMessage by WarningMessage.message.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            kotlinx.coroutines.delay(2000) // dá tempo para ler a mensagem
            navController.popBackStack()
            viewModel.clearState()
        }
    }

    ColumnBackgroundColor(
        horizontal = Alignment.CenterHorizontally,
        vertical = Arrangement.Center
    ) {
        warningMessage?.let { message ->
            Box(contentAlignment = Alignment.TopCenter) {
                CustomMessageBox(
                    message = message,
                    isSuccess = state.isSuccess
                )
            }
        }

        if (warningMessage == null) {
            TitleForgotPassword()
        } else {
            TextCustom(text = "") // espaço vazio quando exibe alerta
        }

        SpacerCustom(paddingBottom = 50.dp)
        CardForgotPassword(viewModel)
    }
}

@Composable
fun TitleForgotPassword() {
    // Título
    TextCustom(
        text = stringResource(id = R.string.txt_title_forgot_password),
        fontSize = 22.sp
    )
}

@Composable
fun CardForgotPassword(
    viewModel: ForgotPasswordViewModel = getViewModel()
) {
    var userEmail by rememberSaveable { mutableStateOf("") }

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
                text = stringResource(id = R.string.txt_message_send_email),
                fontSize = 18.sp
            )

            SpacerCustom(paddingBottom = 30.dp)

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

            ButtomCustom(
                onClick = {
                    viewModel.onSendResetPasswordClick(userEmail)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.btn_send_code),
                    color = DarkGrey
                )
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