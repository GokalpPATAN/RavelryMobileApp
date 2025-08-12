package com.example.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.signup.SignUpScreen

fun NavGraphBuilder.signUpScreen(
    navigateToLogin: () -> Unit
) {
    composable<RegisterScreenRoute> {
        SignUpScreen(
            onNavigateToLogin = navigateToLogin
        )
    }
}