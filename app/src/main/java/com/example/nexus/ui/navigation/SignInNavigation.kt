package com.example.nexus.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.nexus.ui.ViewModels.SignInViewModel
import com.example.nexus.ui.navigation.routes.AuthNavigationGraph
import com.example.nexus.ui.theme.screens.SignInScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.R)
fun NavGraphBuilder.signInNavigation(
    onNavigationToSignUp: () -> Unit,
    onNavigationToHome: () -> Unit,
    onNavigationToForgotPassword: () -> Unit
) {

    composable(AuthNavigationGraph.SIGN_IN) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val scope = rememberCoroutineScope()
        val singInIsSuccessful by viewModel.signInIsSuccessful.collectAsState(false)

        LaunchedEffect(singInIsSuccessful) {
            if (singInIsSuccessful) {
                onNavigationToHome()
            }
        }
        SignInScreen(
            uiState = uiState,
            onEnterClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onNavigationToSignUp = onNavigationToSignUp,
            onNavigationToForgotPassword = onNavigationToForgotPassword,
        )
    }
}

fun NavHostController.navigateToSignIn() {
    navigate(AuthNavigationGraph.SIGN_IN)
}