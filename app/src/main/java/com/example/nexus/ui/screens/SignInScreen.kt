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
import com.example.nexus.ui.components.texts.CustomMessageBox
import com.example.nexus.ui.components.options.CustomSwitch
import com.example.nexus.ui.components.buttons.TextButtonCustom
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
import com.example.nexus.ui.util.BiometricAuth

@Composable
fun SignInScreen(
    uiState: SignInState,
    onEnterClick: (UserModel) -> Unit,
    onBiometricSuccess: () -> Unit,
    onBiometricError: (String) -> Unit,
    onNavigationToForgotPassword: () -> Unit = {},
    onNavigationToSignUp: () -> Unit = {}
) {
    var localWarning by rememberSaveable { mutableStateOf<String?>(null) }
    val warningMessage = localWarning ?: uiState.warningMessage

    ColumnBackgroundColor(
        horizontal = Alignment.CenterHorizontally,
        vertical = Arrangement.Center
    ) {
        warningMessage?.let { message ->
            Box(contentAlignment = Alignment.TopCenter) {
                CustomMessageBox(
                    message = message,
                    isSuccess = uiState.isSuccessful
                )
            }
        }
        Title()
        AnimatedBorderCard(
            modifier = Modifier
                .width(300.dp)
                .padding(2.dp)
                .height(520.dp),
            shape = RoundedCornerShape(50.dp, 0.dp, 50.dp, 0.dp),
            borderGradient = Brush.sweepGradient(listOf(LightGreen, NeonGreen)),
            animationDuration = 5000
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Credentials(uiState)
                CheckElements(uiState)
                AccountArea(
                    uiState = uiState,
                    onEnterClick = onEnterClick,
                    onBiometricSuccess = onBiometricSuccess,
                    onBiometricError = onBiometricError,
                    onNavigationToForgotPassword = onNavigationToForgotPassword,
                    onNavigationToSignUp = onNavigationToSignUp
                )
            }
        }
    }
}

@Composable
fun Title() {
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
}

@Composable
fun Credentials(uiState: SignInState) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextFieldCustom(
            value = uiState.email,
            onValueChange = { uiState.onEmailChange(it) },
            hint = stringResource(id = R.string.hint_user_email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        SpacerCustom(paddingBottom = 20.dp)
        TextFieldCustom(
            value = uiState.password,
            onValueChange = { uiState.onPasswordChange(it) },
            hint = stringResource(id = R.string.hint_password),
            icon = R.drawable.ic_pwd,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            showTrailingIcon = true,
            onTrailingIconClick = { passwordVisibility = !passwordVisibility },
            trailingIcon = if (passwordVisibility) R.drawable.icon_visibility else R.drawable.icon_visibility_off
        )
    }
}

@Composable
fun CheckElements(uiState: SignInState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomSwitch(
                checked = uiState.isRememberMeChecked,
                onCheckedChange = { uiState.onRememberMeClick() }
            )
            TextButtonCustom(onClick = { uiState.onRememberMeClick() }) {
                TextCustom(text = stringResource(id = R.string.txt_remember_me), fontSize = 14.sp)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomSwitch(
                checked = uiState.isBiometricCheck,
                onCheckedChange = { uiState.onBiometricClick() }
            )
            SpacerCustom(paddingEnd = 16.dp)
            TextCustom(text = stringResource(id = R.string.switch_biometric), fontSize = 16.sp)
        }
    }
}


@Composable
fun AccountArea(
    uiState: SignInState,
    onEnterClick: (UserModel) -> Unit,
    onBiometricSuccess: () -> Unit,
    onBiometricError: (String) -> Unit,
    onNavigationToForgotPassword: () -> Unit = {},
    onNavigationToSignUp: () -> Unit = {}
) {
    ButtomCustom(onClick = { onEnterClick(UserModel(uiState.email, uiState.password)) }) {
        Text(text = stringResource(id = R.string.btn_sing_in), color = DarkGrey)
    }
    SpacerCustom(paddingBottom = 16.dp)
    BiometricAuth(
        onSuccess = onBiometricSuccess,
        onError = onBiometricError
    )
    SpacerCustom(paddingBottom = 16.dp)
    TextButtonCustom(onClick = onNavigationToForgotPassword) {
        TextCustom(text = stringResource(id = R.string.btn_forgot_password), fontSize = 14.sp)
    }
    TextButtonCustom(onClick = onNavigationToSignUp) {
        TextCustom(text = stringResource(id = R.string.btn_register), fontSize = 14.sp)
    }
}

@Composable
@Preview
private fun ComponentsPreview() {
    val uiState = SignInState()
    CheckElements(uiState)
}

@Composable
@Preview
private fun SignInScreenPreview() {
    val uiState = SignInState()
    SignInScreen(
        uiState = uiState,
        onEnterClick = {},
        onBiometricSuccess = {},
        onBiometricError = {},
        onNavigationToForgotPassword = {},
        onNavigationToSignUp = {}
    )
}

@Composable
@Preview("With Error")
private fun ErrorPreview() {
    val uiState = SignInState(warningMessage = "Erro ao carregar dados")
    SignInScreen(
        uiState = uiState,
        onEnterClick = {},
        onBiometricSuccess = {},
        onBiometricError = {},
        onNavigationToForgotPassword = {},
        onNavigationToSignUp = {}
    )
}
