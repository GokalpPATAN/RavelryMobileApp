package com.patan.moviecatchcompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.navigation.AppRoute
import com.example.navigation.HomeScreenRoute
import com.example.navigation.LoginScreenRoute
import com.example.navigation.RegisterScreenRoute
import com.example.navigation.loginScreen
import com.example.navigation.signUpScreen
import com.example.presentation.navigation.homeScreen

@Composable
internal fun AppScreenContent(
    navigationController: NavHostController,
    state: AppScreenContract.State,
) {
    if (!state.keepSplashScreenOn) {
        NavHost(
            navController = navigationController,
            route = AppRoute::class,
            startDestination = LoginScreenRoute::class,
            builder = {
                loginScreen(
                    navigateToHome = {
                        navigationController.navigate(HomeScreenRoute) {
                            popUpTo(LoginScreenRoute) { inclusive = true }
                        }
                    },
                    navigateToRegister = {
                        navigationController.navigate(RegisterScreenRoute)
                    }
                )
                signUpScreen(
                    navigateToLogin = {
                        navigationController.navigate(LoginScreenRoute)
                    }
                )
                homeScreen()
            }
        )
    }
}
