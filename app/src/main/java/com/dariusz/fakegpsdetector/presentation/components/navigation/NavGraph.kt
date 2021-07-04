package com.dariusz.fakegpsdetector.presentation.components.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dariusz.fakegpsdetector.di.RepositoryModule.getLocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen.CellTowerScreen
import com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen.CellTowerScreenViewModel
import com.dariusz.fakegpsdetector.presentation.screens.infoscreen.InfoScreen
import com.dariusz.fakegpsdetector.presentation.screens.infoscreen.InfoScreenViewModel
import com.dariusz.fakegpsdetector.presentation.screens.infoscreen.InfoScreenViewModelFactory
import com.dariusz.fakegpsdetector.presentation.screens.mapscreen.MapScreen
import com.dariusz.fakegpsdetector.presentation.screens.mapscreen.MapScreenViewModel
import com.dariusz.fakegpsdetector.presentation.screens.routerscreen.RouterScreen
import com.dariusz.fakegpsdetector.presentation.screens.routerscreen.RouterScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MainNavigationHost(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.AppScreens.MapScreen.route
    ) {
        composable(Screens.AppScreens.MapScreen.route) {
            val viewModel: MapScreenViewModel = viewModel()
            MapScreen(viewModel)
        }
        composable(Screens.AppScreens.RouterScreen.route) {
            val viewModel: RouterScreenViewModel = viewModel()
            RouterScreen(viewModel)
        }
        composable(Screens.AppScreens.CellTowerScreen.route) {
            val viewModel: CellTowerScreenViewModel = viewModel()
            CellTowerScreen(viewModel)
        }
        composable(Screens.AppScreens.InfoScreen.route) {
            val viewModel: InfoScreenViewModel = viewModel(
                factory = InfoScreenViewModelFactory(
                    getLocationFromApiResponseRepository()
                )
            )
            InfoScreen(viewModel)
        }
    }
}
