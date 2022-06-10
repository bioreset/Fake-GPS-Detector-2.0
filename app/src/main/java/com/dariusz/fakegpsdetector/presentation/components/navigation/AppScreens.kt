package com.dariusz.fakegpsdetector.presentation.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CellTower
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Router
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
        object MapScreen : AppScreens("home", "Home", Icons.Default.Map)
        object RouterScreen : AppScreens("routers", "Routers", Icons.Filled.Router)
        object CellTowerScreen : AppScreens("celltowers", "Cell Towers", Icons.Filled.CellTower)
        object InfoScreen : AppScreens("info", "Info", Icons.Filled.Info)
    }
}

val screensBottomNav = listOf(
    Screens.AppScreens.MapScreen,
    Screens.AppScreens.RouterScreen,
    Screens.AppScreens.CellTowerScreen,
    Screens.AppScreens.InfoScreen
)
