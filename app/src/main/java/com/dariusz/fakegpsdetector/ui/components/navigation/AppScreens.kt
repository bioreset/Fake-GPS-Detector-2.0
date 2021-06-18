package com.dariusz.fakegpsdetector.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val title: String) {

    sealed class AppScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens(
        route,
        title
    ) {
        object FirstScreen : AppScreens("home", "Home", Icons.Filled.Home)
        object SecondScreen : AppScreens("routers", "Routers", Icons.Filled.Home)
        object ThirdScreen : AppScreens("celltowers", "Cell Towers", Icons.Filled.Home)
        object FourthScreen : AppScreens("info", "Info", Icons.Filled.Home)
    }
}

val screensBottomNav = listOf(
    Screens.AppScreens.FirstScreen,
    Screens.AppScreens.SecondScreen,
    Screens.AppScreens.ThirdScreen,
    Screens.AppScreens.FourthScreen
)
