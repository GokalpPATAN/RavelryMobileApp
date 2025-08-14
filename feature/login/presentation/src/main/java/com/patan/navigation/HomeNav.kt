package com.patan.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeGraph(
    onNavigateLogin: () -> Unit
) {
    composable(Routes.Home.value) {
        HomeRoute(onNavigateLogin = onNavigateLogin)
    }
}