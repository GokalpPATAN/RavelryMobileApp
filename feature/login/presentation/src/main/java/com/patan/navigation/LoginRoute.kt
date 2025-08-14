package com.patan.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.patan.presentation.login.LoginScreen


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