package com.example.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.login.LoginScreen
import com.example.navigation.LoginScreenRoute


fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit
) {
    composable<LoginScreenRoute> {
        LoginScreen(
            onNavigateToHome = navigateToHome,
            onNavigateToRegister = navigateToRegister
        )
    }
}