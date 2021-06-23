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
        object MapScreen : AppScreens("home", "Home", Icons.Filled.Home)
        object RouterScreen : AppScreens("routers", "Routers", Icons.Filled.Home)
        object CellTowerScreen : AppScreens("celltowers", "Cell Towers", Icons.Filled.Home)
        object InfoScreen : AppScreens("info", "Info", Icons.Filled.Home)
    }
}

val screensBottomNav = listOf(
    Screens.AppScreens.MapScreen,
    Screens.AppScreens.RouterScreen,
    Screens.AppScreens.CellTowerScreen,
    Screens.AppScreens.InfoScreen
)
