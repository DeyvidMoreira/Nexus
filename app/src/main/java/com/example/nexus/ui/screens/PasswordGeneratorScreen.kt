package com.example.nexus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.ui.components.CustomMessageBox
import com.example.nexus.ui.components.CustomSlider
import com.example.nexus.ui.components.CustomSwitch
import com.example.nexus.ui.components.drawer.Drawer
import com.example.nexus.ui.components.TopBar
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MediumGrey
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.until.WarningMessage
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
    val warningMessage by WarningMessage.message.collectAsState()
    val currentWarningMessage = warningMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Drawer { onOpenDrawer ->
            Scaffold(
                topBar = {
                    TopBar(onOpenDrawer = onOpenDrawer)
                }

            ) { contentPadding ->
                ColumnBackgroundColor (){
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,

                        ){

                        // Mensagem de aviso
                        currentWarningMessage?.let { message ->
                            Box(
                                contentAlignment = Alignment.TopCenter
                            ) {
                                CustomMessageBox(
                                    message = message,
                                    isSuccess = uiState.isPasswordSaved
                                )
                            }
                        }
                        // Título e descrição
                        if (currentWarningMessage == null) {
                            //SpacerCustom(paddingBottom = 16.dp)
                            TextCustom(
                                text = stringResource(R.string.title),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            SpacerCustom(paddingBottom = 32.dp)
                        } else {
                            TextCustom(text = "")

                        }
                        //Senha gerada
                        Card(
                            modifier = Modifier
                                .width(280.dp),
                            shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp),
                            colors = CardDefaults.cardColors(MediumGrey),
                            elevation = CardDefaults.cardElevation(15.dp),

                            ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                uiState.generatedPassword?.let { password ->
                                    Text(
                                        text = password,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = NeonGreen,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }

                    // Card de menu de opções
                    AnimatedBorderCard(
                        modifier = Modifier
                            .width(280.dp)
                            .padding(contentPadding),
                        animationDuration = 3000,
                        shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp),
                        shadowElevation = 15.dp
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
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
                            SpacerCustom(paddingBottom = 16.dp)
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
                            SpacerCustom(paddingBottom = 16.dp)
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
                            SpacerCustom(paddingBottom = 16.dp)
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
                    SpacerCustom(paddingBottom = 32.dp)

                    TextCustom(
                        text = stringResource(R.string.password_length) + uiState.passwordLength,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    CustomSlider(
                        value = uiState.sliderValue,
                        onValueChange = { value ->
                            sliderValueChange(value)
                            passwordLengthChange(value.toInt())
                        },
                        valueRange = 0f..20f
                    )
                    SpacerCustom(paddingBottom = 32.dp)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
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
    val uiState = GeneratorState(warningMessage = "Erro ao carregar dados")
    PasswordGeneratorScreen(uiState, navController)
}