package com.dariusz.fakegpsdetector.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen.CellTowerScreen
import com.dariusz.fakegpsdetector.presentation.screens.infoscreen.InfoScreen
import com.dariusz.fakegpsdetector.presentation.screens.mapscreen.MapScreen
import com.dariusz.fakegpsdetector.presentation.screens.routerscreen.RouterScreen

@Composable
fun MainNavigationHost(
    navController: NavController
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.AppScreens.MapScreen.route
    ) {
        composable(Screens.AppScreens.MapScreen.route) {
            MapScreen()
        }
        composable(Screens.AppScreens.RouterScreen.route) {
            RouterScreen()
        }
        composable(Screens.AppScreens.CellTowerScreen.route) {
            CellTowerScreen()
        }
        composable(Screens.AppScreens.InfoScreen.route) {
            InfoScreen()
        }
    }
}
