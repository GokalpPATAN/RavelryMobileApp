package com.patan.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.navigateSingleTop(
    route: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) = navigate(route) {
    launchSingleTop = true
    builder()
}

fun NavController.navigateAndClearTo(
    route: String,
    popUpToRoute: String,
    inclusive: Boolean = true
) = navigateSingleTop(route) {
    popUpTo(popUpToRoute) { this.inclusive = inclusive }
}