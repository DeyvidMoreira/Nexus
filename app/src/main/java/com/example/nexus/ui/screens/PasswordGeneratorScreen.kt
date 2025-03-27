package com.example.nexus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nexus.R
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.dialogs.LogoutDialog
import com.example.nexus.ui.components.dialogs.WarningDialog
import com.example.nexus.ui.components.texts.CustomMessageBox
import com.example.nexus.ui.components.options.CustomSlider
import com.example.nexus.ui.components.options.CustomSwitch
import com.example.nexus.ui.components.drawer.Drawer
import com.example.nexus.ui.components.itens.TopBar
import com.example.nexus.ui.navigation.MainAppRoute
import com.example.nexus.ui.navigation.routes.AuthNavigationGraph
import com.example.nexus.ui.states.GeneratorState
import com.example.nexus.ui.theme.DarkGrey
import com.example.nexus.ui.theme.DarkMediumGrey
import com.example.nexus.ui.theme.MediumGrey
import com.example.nexus.ui.theme.NeonGreen
import com.example.nexus.ui.theme.components.AnimatedBorderCard
import com.example.nexus.ui.theme.components.ButtomCustom
import com.example.nexus.ui.theme.components.ColumnBackgroundColor
import com.example.nexus.ui.theme.components.SpacerCustom
import com.example.nexus.ui.theme.components.TextCustom
import com.example.nexus.ui.until.ClipboardHelper
import com.example.nexus.ui.until.WarningMessage
import com.example.pwdcripto.framework.contants.ConstantsCharacters
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

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

    LaunchedEffect(uiState) {
        uiState.isPasswordSaved = false
        uiState.isPasswordDeleted = false
        uiState.isAccountDeleted = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            topBar = {
                MyTopBar(navController, uiState)
            }

        ) { contentPadding ->
            ColumnBackgroundColor(
                horizontal = Alignment.CenterHorizontally,
                vertical = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .padding(contentPadding)
                        .background(
                            brush = Brush.sweepGradient(
                                listOf(
                                    MediumGrey,
                                    DarkMediumGrey,
                                    MediumGrey
                                )
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    SpacerCustom(paddingBottom = 8.dp)
                    HeaderArea(uiState)
                    SpacerCustom(paddingBottom = 16.dp)
                    OptionsArea(
                        uiState,
                        upperChange,
                        lowerChange,
                        numChange,
                        especialChange
                    )
                    SpacerCustom(paddingBottom = 32.dp)
                    SliderArea(uiState, passwordLengthChange, sliderValueChange)
                    SpacerCustom(paddingBottom = 32.dp)
                    ButtonsArea(generatePassword, dialogChange, bottomSheetChange)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(navController: NavController, uiState: GeneratorState) {

    var expanded by remember { mutableStateOf(false) }
    val showLogoutDialog = remember { mutableStateOf(false) }
    val showDeleteAllPasswordsDialog = remember { mutableStateOf(false) }
    val showDeleteAccountDialog = remember { mutableStateOf(false) }
    val viewModel: PwdGeneratorViewModel = viewModel()

    //Logout
    if (showLogoutDialog.value) {
        LogoutDialog(
            onDismiss = { showLogoutDialog.value = false },
            viewModel = viewModel(),
            navController = navController
        )
    }

    //Deletar Senhas
    if (showDeleteAllPasswordsDialog.value) {
        WarningDialog(
            title = stringResource(R.string.title_delete_all_passwords),
            message = stringResource(R.string.message_delete_all_passwords),
            onConfirm = {
                uiState.isPasswordDeleted = true
                viewModel.deleteAllPasswords()
            },
            onDismiss = {
                showDeleteAllPasswordsDialog.value = false
                uiState.isPasswordDeleted = false
            },
            viewModel = viewModel(),
            backgroundColor = DarkGrey,
            password = PasswordEntity()
        )
    }

    //Deletar Conta
    if (showDeleteAccountDialog.value) {
        WarningDialog(
            title = stringResource(R.string.title_delete_account),
            message = stringResource(R.string.message_delete_account),
            onConfirm = {
                viewModel.deleteAccount()
                navController.navigate(AuthNavigationGraph.SIGN_IN) {
                    popUpTo(MainAppRoute.MAIN) { inclusive = true }
                }
            },
            onDismiss = { showDeleteAccountDialog.value = false },
            viewModel = viewModel(),
            backgroundColor = DarkGrey,
            password = PasswordEntity()
        )
    }

    TopAppBar(
        title = { },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.Settings, contentDescription = "Menu", tint = NeonGreen)
            }
            DropdownMenu(
                modifier = Modifier
                    .background(DarkMediumGrey),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(DarkMediumGrey)
                ) {
                    DropdownMenuItem(
                        text = { TextCustom("Sobre") },
                        onClick = {

                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { TextCustom("Apagar Senhar") },
                        onClick = {
                            showDeleteAllPasswordsDialog.value = true
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { TextCustom("Deletar Conta") },
                        onClick = {
                            showDeleteAccountDialog.value = true
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { TextCustom("Logout") },
                        onClick = {
                            showLogoutDialog.value = true
                            expanded = false
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}


@Composable
fun HeaderArea(uiState: GeneratorState) {
    val warningMessage by WarningMessage.message.collectAsState()
    val currentWarningMessage = warningMessage
    val context = LocalContext.current
    // Mensagem de aviso
    currentWarningMessage?.let { message ->
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            if (uiState.isPasswordDeleted || uiState.isAccountDeleted || uiState.isPasswordSaved) {
                CustomMessageBox(
                    message = message,
                    isSuccess = true
                )
            } else {
                CustomMessageBox(
                    message = message,
                    isSuccess = false
                )
            }

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

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (uiState.generatedPassword != null) {
            IconButton(
                modifier = Modifier
                    .padding(2.dp),
                onClick = {
                    uiState.generatedPassword?.let { password ->
                        ClipboardHelper().copyClipboard(context, password, uiState)
                    }
                },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_copy),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = NeonGreen
                )
            }
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
}

@Composable
fun OptionsArea(
    uiState: GeneratorState,
    upperChange: (Boolean) -> Unit = {},
    lowerChange: (Boolean) -> Unit = {},
    numChange: (Boolean) -> Unit = {},
    especialChange: (Boolean) -> Unit = {}
) {
    // Card de menu de opções
    AnimatedBorderCard(
        modifier = Modifier
            .width(280.dp)
            .padding(16.dp),
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

}

@Composable
fun SliderArea(
    uiState: GeneratorState,
    passwordLengthChange: (Int) -> Unit = {},
    sliderValueChange: (Float) -> Unit = {},
) {
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
}

@Composable
fun ButtonsArea(
    generatePassword: () -> Unit = {},
    dialogChange: (Boolean) -> Unit = {},
    bottomSheetChange: (Boolean) -> Unit = {}
) {
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


@Preview
@Composable
private fun AreaPreview() {
    val uiState = GeneratorState()
    HeaderArea(uiState)
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


@Preview
@Composable
private fun MyTopBarPreview() {
    val navController = rememberNavController()
    val uiState = GeneratorState(warningMessage = "Erro ao carregar dados")
    MyTopBar(navController, uiState)
}