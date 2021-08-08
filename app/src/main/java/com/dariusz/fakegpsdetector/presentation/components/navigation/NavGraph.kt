package com.dariusz.fakegpsdetector.presentation.components.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideCellTowersDataRepository
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideLocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideLocationRepository
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideWifiNodesRepository
import com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen.CellTowerScreen
import com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen.CellTowerScreenViewModel
import com.dariusz.fakegpsdetector.presentation.screens.infoscreen.InfoScreen
import com.dariusz.fakegpsdetector.presentation.screens.infoscreen.InfoScreenViewModel
import com.dariusz.fakegpsdetector.presentation.screens.mapscreen.MapScreen
import com.dariusz.fakegpsdetector.presentation.screens.mapscreen.MapScreenViewModel
import com.dariusz.fakegpsdetector.presentation.screens.routerscreen.RouterScreen
import com.dariusz.fakegpsdetector.presentation.screens.routerscreen.RouterScreenViewModel
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.composeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("NewApi")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MainNavigationHost(
    navController: NavController,
    context: Context
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.AppScreens.MapScreen.route
    ) {
        composable(Screens.AppScreens.MapScreen.route) {
            val mapScreenViewModel = composeViewModel {
                MapScreenViewModel(
                    provideLocationRepository(context)
                )
            }
            MapScreen(mapScreenViewModel)
        }
        composable(Screens.AppScreens.RouterScreen.route) {
            val routerScreenViewModel = composeViewModel {
                RouterScreenViewModel(
                    provideWifiNodesRepository(context)
                )
            }
            RouterScreen(routerScreenViewModel)
        }
        composable(Screens.AppScreens.CellTowerScreen.route) {
            val cellTowerScreenViewModel = composeViewModel {
                CellTowerScreenViewModel(
                    provideCellTowersDataRepository(context)
                )
            }
            CellTowerScreen(cellTowerScreenViewModel)
        }
        composable(Screens.AppScreens.InfoScreen.route) {
            val infoScreenViewModel = composeViewModel {
                InfoScreenViewModel(
                    provideLocationFromApiResponseRepository(),
                    provideLocationRepository(context),
                    provideWifiNodesRepository(context),
                    provideCellTowersDataRepository(context)
                )
            }
            InfoScreen(infoScreenViewModel)
        }
    }
}
