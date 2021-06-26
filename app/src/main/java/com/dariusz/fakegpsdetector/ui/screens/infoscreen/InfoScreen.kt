package com.dariusz.fakegpsdetector.ui.screens.infoscreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dariusz.fakegpsdetector.ui.components.common.BaseDetail
import com.dariusz.fakegpsdetector.utils.DistanceCalculator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun InfoScreen(viewModel: InfoScreenViewModel = InfoScreenViewModel()) {
    val currentContext = LocalContext.current
    viewModel.initScreenTasks(currentContext)
    if (Build.VERSION.SDK_INT < 29) {
        viewModel.getCellTowersData(currentContext)
    } else {
        viewModel.getCellTowersDataLiveApi29Plus(currentContext)
    }
    val currentLocation by remember(viewModel) { viewModel.currentLocation }.collectAsState()
    val currentWifiRouters by remember(viewModel) { viewModel.currentRouters }.collectAsState()
    val currentCellTowers by remember(viewModel) { viewModel.currentCellTowers }.collectAsState()
    val apiResponse by remember(viewModel) { viewModel.apiResponse }.collectAsState()
    val distanceCalculator = DistanceCalculator(currentLocation, apiResponse.location)
    val result = distanceCalculator.isRealLocation(apiResponse.accuracy ?: 0)
    val verdict = if (result) "True" else "Spoofed"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(start = 6.dp)
    ) {
        BaseDetail(
            "Current Location: ",
            "lat: ${currentLocation.latitude}, lng: ${currentLocation.longitude}",

        )
        BaseDetail(
            "Current Location From API: ",
            "lat: ${apiResponse.location.lat}, lng: ${apiResponse.location.lng}, accuracy: ${apiResponse.accuracy} "
        )
        BaseDetail("Verdict: ", verdict)
        Button(
            onClick = {
                viewModel.submitRequest(currentContext, currentCellTowers, currentWifiRouters)
            },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Submit")
        }
    }
}
