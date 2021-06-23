package com.dariusz.fakegpsdetector.ui.screens.infoscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.BaseCard
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import com.dariusz.fakegpsdetector.utils.DistanceCalculator.calculateDistance
import com.dariusz.fakegpsdetector.utils.DistanceCalculator.isRealLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun InfoScreen(viewModel: InfoScreenViewModel = InfoScreenViewModel()) {
    val viewModelForNavigation: MainViewModel = viewModel()
    viewModelForNavigation.setCurrentScreen(Screens.AppScreens.InfoScreen)
    val currentContext = LocalContext.current
    viewModel.initScreenTasks(currentContext)
    val currentLocation by remember(viewModel) { viewModel.currentLocation }.collectAsState()
    val currentWifiRouters by remember(viewModel) { viewModel.currentRouters }.collectAsState()
    val currentCellTowers by remember(viewModel) { viewModel.currentCellTowers }.collectAsState()
    viewModel.manageTheResponse(currentContext, currentCellTowers, currentWifiRouters)
    viewModel.getLocationFromApisResponse(currentContext)
    val apiResponse by remember(viewModel) { viewModel.apiResponse }.collectAsState()
    val calculator = calculateDistance(
        currentLocation.latitude,
        currentLocation.longitude,
        apiResponse.lat ?: 0.0,
        apiResponse.lng ?: 0.0
    )
    val result = isRealLocation(
        calculator,
        apiResponse.accuracy ?: 0
    )
    val verdict = if (result) "True" else "Spoofed"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        BaseCard(
            "Current Location: ",
            "lat: ${currentLocation.latitude}, lng: ${currentLocation.longitude}"
        )
        BaseCard(
            "Current Location From API: ",
            "lat: ${apiResponse.lat}, lng: ${apiResponse.lng}, accuracy: ${apiResponse.accuracy} "
        )
        BaseCard("Verdict: ", verdict)
    }
}


