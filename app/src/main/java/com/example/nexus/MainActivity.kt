package com.example.nexus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nexus.ui.components.NavigationGraph
import com.example.nexus.ui.theme.screens.ForgotPasswordScreen
import com.example.nexus.ui.theme.screens.SingInScreen
import com.example.nexus.ui.theme.screens.SingUpScreenScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationGraph()
        }
    }

    @Composable
    fun AppNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { SingInScreen(navController) }
            composable("register") { SingUpScreenScreen(navController) }
            composable("forgotPassword") { ForgotPasswordScreen(navController) }
        }
    }

    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        AppNavHost(navController = navController)
    }
}
