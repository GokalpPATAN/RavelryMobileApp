package com.patan.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.splashGate(
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    composable(Routes.Splash.value) {
        AuthGateRoute(
            onAuthenticated = onAuthenticated,
            onUnauthenticated = onUnauthenticated
        )
    }
}