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

        // dispara navegação ao sucesso
        val signedIn by viewModel.signInIsSuccessful.collectAsState(initial = false)
        if (signedIn) onNavigationToHome()

        LaunchedEffect (viewModel){
            viewModel.signInIsSuccessful.collect{ success ->
                if(success) {
                    onNavigationToHome()
                    viewModel.resetLoginState() //Reseta após navegar
                }

            }
        }

        SignInScreen(
            uiState = uiState,
            onEnterClick = { user -> scope.launch { viewModel.signIn(user) } },
            onBiometricSuccess = { scope.launch { viewModel.signInWithSavedCredentials() } },
            onBiometricError = { msg -> scope.launch { viewModel.emitWarning(msg) } },
            onNavigationToForgotPassword = onNavigationToForgotPassword,
            onNavigationToSignUp = onNavigationToSignUp
        )
    }
}

fun NavHostController.navigateToSignIn() {
    navigate(AuthNavigationGraph.SIGN_IN)
}