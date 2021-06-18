package com.dariusz.fakegpsdetector.ui.components.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dariusz.fakegpsdetector.ui.screens.firstscreen.FirstScreen
import com.dariusz.fakegpsdetector.ui.screens.firstscreen.FirstScreenViewModel
import com.dariusz.fakegpsdetector.ui.screens.fourthscreen.FourthScreen
import com.dariusz.fakegpsdetector.ui.screens.fourthscreen.FourthScreenViewModel
import com.dariusz.fakegpsdetector.ui.screens.secondscreen.SecondScreen
import com.dariusz.fakegpsdetector.ui.screens.secondscreen.SecondScreenViewModel
import com.dariusz.fakegpsdetector.ui.screens.thirdscreen.ThirdScreen
import com.dariusz.fakegpsdetector.ui.screens.thirdscreen.ThirdScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MainNavigationHost(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.AppScreens.FirstScreen.route
    ) {
        composable(Screens.AppScreens.FirstScreen.route) {
            val viewModel: FirstScreenViewModel = viewModel()
            FirstScreen(viewModel)
        }
        composable(Screens.AppScreens.SecondScreen.route) {
            val viewModel: SecondScreenViewModel = viewModel()
            SecondScreen(viewModel)
        }
        composable(Screens.AppScreens.ThirdScreen.route) {
            val viewModel: ThirdScreenViewModel = viewModel()
            ThirdScreen(viewModel)
        }
        composable(Screens.AppScreens.FourthScreen.route) {
            val viewModel: FourthScreenViewModel = viewModel()
            FourthScreen(viewModel)
        }
    }
}
