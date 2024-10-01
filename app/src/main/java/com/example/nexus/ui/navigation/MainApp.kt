package com.example.nexus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.screens.DashboardScreen
import com.example.nexus.ui.screens.Test1
import com.example.nexus.ui.screens.Test2
import com.example.nexus.ui.screens.Test3
import com.example.nexus.ui.states.SignUpUiState
import com.example.nexus.ui.theme.screens.ForgotPasswordScreen
import com.example.nexus.ui.theme.screens.SignUpScreen
import com.example.nexus.ui.theme.screens.SingInScreen


object MainAppRoute {
    const val AUTH = "auth"  // Rota para o fluxo de autenticação
    const val MAIN = "main"  // Rota para o fluxo principal do aplicativo
}
/**
 * Main Composable function responsible for setting up the app's navigation system.
 */
@Composable
fun MainApp() {
    val navController = rememberNavController()

    // Host for navigation
    NavHost(navController = navController, startDestination = MainAppRoute.AUTH) {

        // Authentication Flow
        navigation(startDestination = AuthNavigationGraph.SIGN_IN, route = MainAppRoute.AUTH) {
            composable(AuthNavigationGraph.SIGN_IN) {
                SingInScreen(navController) {
                    // After login, navigate to the main flow
                    navController.navigate(MainAppRoute.MAIN) {
                        popUpTo(MainAppRoute.AUTH) { inclusive = true }
                    }
                }
            }
            // Chame a função signUpNavigation aqui
            signUpNavigation {
                // Se precisar navegar para a tela de login após o cadastro, pode passar a função de navegação aqui
                navController.navigate(AuthNavigationGraph.SIGN_IN) {
                    popUpTo(AuthNavigationGraph.SIGN_IN) { inclusive = true }
                }
            }
            composable(AuthNavigationGraph.FORGOT_PASSWORD) { ForgotPasswordScreen(navController) }
        }

        // Main App Flow
        navigation(startDestination = MainNavigationGraph.DASHBOARD, route = MainAppRoute.MAIN) {
            composable(MainNavigationGraph.DASHBOARD) { DashboardScreen(navController) }
            composable(MainNavigationGraph.TEST1) { Test1(navController) }
            composable(MainNavigationGraph.TEST2) { Test2(navController) }
            composable(MainNavigationGraph.TEST3) { Test3(navController) }
        }
    }
}