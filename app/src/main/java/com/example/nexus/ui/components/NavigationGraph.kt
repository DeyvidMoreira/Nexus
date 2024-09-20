package com.example.nexus.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.theme.screens.CodeConfirmationScreen
import com.example.nexus.ui.theme.screens.ForgotPasswordScreen
import com.example.nexus.ui.theme.screens.SingInScreen
import com.example.nexus.ui.theme.screens.NewPassowordScreen
import com.example.nexus.ui.theme.screens.SingUpScreenScreen


object NavigationGraph {
    const val LOGIN = "LoginScreen"
    const val REGISTER = "RegisterScreen"
    const val FORGOT_PASSWORD = "ForgotPasswordScreen"
    const val NEW_PASSWORD = "NewPasswordScreen"
    const val CODE_CONFIRMATION = "CodeConfirmationScreen"
}

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationGraph.LOGIN) {
        composable(route = NavigationGraph.LOGIN) { SingInScreen(navController) }
        composable(route = NavigationGraph.REGISTER) { SingUpScreenScreen(navController) }
        composable(route = NavigationGraph.FORGOT_PASSWORD) { ForgotPasswordScreen(navController) }
        composable(route = NavigationGraph.NEW_PASSWORD) { NewPassowordScreen(navController)}
        composable(route = NavigationGraph.CODE_CONFIRMATION) { CodeConfirmationScreen(navController) }
    }
}