@file:Suppress("UNUSED_EXPRESSION")

package com.example.nexus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.components.CustomSlider
import com.example.nexus.ui.components.CustomSwitch
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.pwdcripto.framework.contants.ConstantsCharacters

@Composable
fun PasswordGeneratorScreen(
    uiState: GeneratorState,
    navController: NavController,
    upperChange: (Boolean) -> Unit = {},
    lowerChange: (Boolean) -> Unit = {},
    numChange: (Boolean) -> Unit = {},
    especialChange: (Boolean) -> Unit = {},
    passwordLengthChange: (Int) -> Unit = {},
    sliderValueChange: (Float) -> Unit = {},
    generatePassword: () -> Unit = {},
    dialogChange: (Boolean) -> Unit = {},
    bottomSheetChange: (Boolean) -> Unit = {}
) {
    Scaffold { contentPadding ->
        ColumnBackgroundColor {
            // Mensagem de erro
            uiState.errorMessage?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Red)
                ) {
                    Text(
                        text = it,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            SpacerCustom(paddingBottom = 8.dp)
            TextCustom(
                text = stringResource(R.string.title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            SpacerCustom(paddingBottom = 8.dp)

            AnimatedBorderCard(
                modifier = Modifier
                    .width(300.dp)
                    .padding(contentPadding)
                    .padding(top = 16.dp),
                animationDuration = 1000,
                shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp),
                shadowElevation = 15.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.generatedPassword?.let { password ->
                        Text(
                            text = stringResource(R.string.password_genered) + password,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CustomSwitch(
                            checked = uiState.upperChecked,
                            onCheckedChange = { isChecked -> upperChange(isChecked) }
                        )
                        SpacerCustom(paddingEnd = 16.dp)
                        TextCustom(
                            text = ConstantsCharacters.UPPER_CASE,
                            fontSize = 16.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CustomSwitch(
                            checked = uiState.lowChecked,
                            onCheckedChange = { isChecked -> lowerChange(isChecked) }
                        )
                        SpacerCustom(paddingEnd = 16.dp)
                        TextCustom(
                            text = ConstantsCharacters.LOWER_CASE,
                            fontSize = 16.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CustomSwitch(
                            checked = uiState.numChecked,
                            onCheckedChange = { isChecked -> numChange(isChecked) }
                        )
                        SpacerCustom(paddingEnd = 16.dp)
                        TextCustom(
                            text = ConstantsCharacters.NUMBERS,
                            fontSize = 16.sp
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CustomSwitch(
                            checked = uiState.especialChecked,
                            onCheckedChange = { isChecked -> especialChange(isChecked) }
                        )
                        SpacerCustom(paddingEnd = 16.dp)
                        TextCustom(
                            text = ConstantsCharacters.SPECIAL_CHARACTERS,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            TextCustom(
                text = stringResource(R.string.password_length) + uiState.passwordLength,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            SpacerCustom(paddingBottom = 16.dp)
            CustomSlider(
                value = uiState.sliderValue,
                onValueChange = { value ->
                    sliderValueChange(value)
                    passwordLengthChange(value.toInt())
                },
                valueRange = 8f..20f
            )
            SpacerCustom(paddingBottom = 16.dp)

            ButtomCustom(onClick = generatePassword) {
                TextCustom(
                    text = stringResource(R.string.generate_button),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            SpacerCustom(paddingBottom = 16.dp)

            ButtomCustom(onClick = { dialogChange(true) }) {
                TextCustom(
                    text = stringResource(R.string.save_button),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            SpacerCustom(paddingBottom = 16.dp)

            ButtomCustom(onClick = { bottomSheetChange(true) }) {
                TextCustom(
                    text = stringResource(R.string.show_button),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}


@Preview
@Composable
private fun DashboardScreeenPreview() {
    val navController = rememberNavController()
    val uiState = GeneratorState()
    PasswordGeneratorScreen(uiState, navController)
}

@Preview
@Composable
private fun DashboardScreeenErrorPreview() {
    val navController = rememberNavController()
    val uiState = GeneratorState(errorMessage = "Erro ao carregar dados")
    PasswordGeneratorScreen(uiState, navController)
}