package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.os.Build
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import com.dariusz.fakegpsdetector.domain.model.LocationData
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.dariusz.fakegpsdetector.presentation.components.common.BaseDetail
import com.dariusz.fakegpsdetector.utils.DistanceCalculator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun InfoScreen(infoScreenViewModel: InfoScreenViewModel = viewModel()) {
    val currentContext = LocalContext.current
    val currentCoroutineScope = rememberCoroutineScope()
    val apiRequestSent = remember { mutableStateOf(false) }
    val apiResponse = remember {
        mutableStateOf(ApiResponseModel(LocationData(0.0, 0.0), 0))
    }
    infoScreenViewModel.initScreenTasks(currentContext)
    if (Build.VERSION.SDK_INT < 29) {
        infoScreenViewModel.getCellTowersData(currentContext)
    } else {
        infoScreenViewModel.getCellTowersDataLiveApi29Plus(currentContext)
    }
    val currentLocation by remember(infoScreenViewModel) { infoScreenViewModel.currentLocation }.collectAsState()
    val currentWifiRouters by remember(infoScreenViewModel) { infoScreenViewModel.currentRouters }.collectAsState()
    val currentCellTowers by remember(infoScreenViewModel) { infoScreenViewModel.currentCellTowers }.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(start = 6.dp)
    ) {
        Button(
            onClick = {
                apiRequestSent.value = true
                infoScreenViewModel.submitRequest(currentCellTowers, currentWifiRouters)
                currentCoroutineScope.launch {
                    infoScreenViewModel.apiResponse.collect {
                        apiResponse.value = it
                    }
                }
            },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Submit")
        }
        if (apiRequestSent.value)
            ManageApiResponse(
                currentLocation = currentLocation,
                apiResponse = apiResponse.value
            )
    }
}

@Composable
fun ManageApiResponse(currentLocation: LocationModel, apiResponse: ApiResponseModel) {
    val distanceCalculator = DistanceCalculator(currentLocation, apiResponse.location)
    val result = distanceCalculator.isRealLocation(apiResponse.accuracy ?: 0)
    val verdict = if (result) "True" else "Spoofed"
    BaseDetail(
        "Current Location: ",
        "lat: ${currentLocation.latitude}, lng: ${currentLocation.longitude}",
    )
    BaseDetail(
        "Current Location From API: ",
        "lat: ${apiResponse.location.lat}, lng: ${apiResponse.location.lng}, accuracy: ${apiResponse.accuracy} "
    )
    BaseDetail("Verdict: ", verdict)
}
