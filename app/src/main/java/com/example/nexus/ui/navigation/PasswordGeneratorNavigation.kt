package com.example.nexus.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.components.PasswordModalBottomSheet
import com.example.nexus.ui.components.SavePasswordDialog
import com.example.nexus.ui.navigation.routes.MainNavigationGraph
import com.example.nexus.ui.screens.PasswordGeneratorScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.passwordGeneratorNavigation(
    navController: NavHostController
) {
    composable(MainNavigationGraph.PasswordGeneratorScreen) {
        val viewModel: PwdGeneratorViewModel = koinViewModel()
        val uiState by viewModel.state.collectAsState()

        var showDialog by remember { mutableStateOf(false) }
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()

        PasswordGeneratorScreen(
            uiState = uiState,
            navController = navController,
            upperChange = { isChecked -> viewModel.updateOption("upper", isChecked) },
            lowerChange = { isChecked -> viewModel.updateOption("lower", isChecked) },
            numChange = { isChecked -> viewModel.updateOption("number", isChecked) },
            especialChange = { isChecked -> viewModel.updateOption("special", isChecked) },
            passwordLengthChange = { length -> viewModel.updatePasswordLength(length) },
            generatePassword = { viewModel.generatePassword() },
            dialogChange = { showDialog = it },
            bottomSheetChange = { showBottomSheet = it }
        )

        if (showDialog) {
            SavePasswordDialog(
                viewModel = viewModel,
                onDismiss = { showDialog = false }
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                PasswordModalBottomSheet(
                    viewModel = viewModel,
                    onDelete = { password -> viewModel.deletePassword(password) }
                )
            }
        }
    }
}

fun NavHostController.navigateToPasswordGenerator() {
    navigate(MainNavigationGraph.PasswordGeneratorScreen)
}
