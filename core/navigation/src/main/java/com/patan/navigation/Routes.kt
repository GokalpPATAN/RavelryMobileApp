package com.patan.navigation

sealed interface Route {
    val value: String
}

object Routes {
    object Splash : Route { override val value = "splash" }
    object Login  : Route { override val value = "login" }
    object Home   : Route { override val value = "home"  }
}

