package com.example.nexus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.navigation.routes.AuthNavigationGraph
import com.example.nexus.ui.navigation.routes.MainNavigationGraph
import com.example.nexus.ui.theme.screens.ForgotPasswordScreen
import com.example.nexus.ui.theme.screens.SingInScreen


object MainAppRoute {
    const val AUTH = "auth"  // Rota para o fluxo de autenticação
    const val MAIN = "main"  // Rota para o fluxo principal do aplicativo
}
@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainAppRoute.AUTH
    ) {
        // Fluxo de autenticação
        navigation(
            startDestination = AuthNavigationGraph.SIGN_IN,
            route = MainAppRoute.AUTH
        ) {
            composable(AuthNavigationGraph.SIGN_IN) {
                SingInScreen(navController) {
                    navController.navigate(MainAppRoute.MAIN) {
                        popUpTo(MainAppRoute.AUTH) { inclusive = true }
                    }
                }
            }
            signUpNavigation {
                navController.navigate(AuthNavigationGraph.SIGN_IN) {
                    popUpTo(AuthNavigationGraph.SIGN_IN) { inclusive = true }
                }
            }
            composable(AuthNavigationGraph.FORGOT_PASSWORD) {
                ForgotPasswordScreen(navController)
            }
        }

        // Fluxo principal
        navigation(
            startDestination = MainNavigationGraph.PasswordGeneratorScreen,
            route = MainAppRoute.MAIN
        ) {
            // Integração com o gerador de senhas
            passwordGeneratorNavigation(navController)

        }
    }
}
