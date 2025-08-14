package com.patan.moviecatchcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.patan.navigation.Routes
import com.patan.navigation.homeGraph
import com.patan.navigation.loginGraph
import com.patan.navigation.navigateAndClearTo
import com.patan.navigation.splashGate

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    // Splash'ı sabit start olarak tutuyoruz; ilk karar burada verilecek
    NavHost(
        navController = nav,
        startDestination = remember { Routes.Splash.value }
    ) {
        // 1) Splash/AuthGate: oturum kontrolü → home/login
        splashGate(
            onAuthenticated = {
                nav.navigateAndClearTo(
                    route = Routes.Home.value,
                    popUpToRoute = Routes.Splash.value,
                    inclusive = true
                )
            },
            onUnauthenticated = {
                nav.navigateAndClearTo(
                    route = Routes.Login.value,
                    popUpToRoute = Routes.Splash.value,
                    inclusive = true
                )
            }
        )

        // 2) Login grafiği
        loginGraph(
            onNavigateHome = {
                // Başarılı girişten sonra back stack'i temizle
                nav.navigateAndClearTo(
                    route = Routes.Home.value,
                    popUpToRoute = Routes.Login.value,
                    inclusive = true
                )
            }
        )

        // 3) Home grafiği
        homeGraph(
            onNavigateLogin = {
                // Logout sonrası login'e dön ve Home'u temizle
                nav.navigateAndClearTo(
                    route = Routes.Login.value,
                    popUpToRoute = Routes.Home.value,
                    inclusive = true
                )
            }
        )
    }
}