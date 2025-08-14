package com.patan.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.patan.feature.login.presentation.login.LoginRoute

fun NavGraphBuilder.loginGraph(
    onNavigateHome: () -> Unit
) {
    composable(Routes.Login.value) {
        LoginRoute(onNavigateHome = onNavigateHome)
    }
}