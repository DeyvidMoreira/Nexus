package com.example.nexus.ui.navigation


import com.example.nexus.ui.ViewModels.SignUpViewModel
import com.example.nexus.ui.theme.screens.SignUpScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.signUpNavigation(
    onNavigationToSignIn: () -> Unit
){
    composable(AuthNavigationGraph.SIGN_UP){
        val viewModel = koinViewModel<SignUpViewModel>()
        val uiState by viewModel.iuState.collectAsState()
        val scope = rememberCoroutineScope()
        val signUpIsSuccessful by viewModel.signUpIsSuccessful.collectAsState(false)
        LaunchedEffect(signUpIsSuccessful) {
            if(signUpIsSuccessful) {
                onNavigationToSignIn()
            }
        }
        SignUpScreen(
            uiState = uiState,
            onSignUpClick = {
                scope.launch {
                    viewModel.signUp()
                }
            }
        )
    }
}

fun NavHostController.navigateToSignUp() {
    navigate(AuthNavigationGraph.SIGN_UP)
}