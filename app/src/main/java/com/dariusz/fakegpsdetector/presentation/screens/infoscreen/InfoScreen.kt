package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.dariusz.fakegpsdetector.presentation.components.common.BaseDetail
import com.dariusz.fakegpsdetector.utils.DistanceCalculator
import com.dariusz.fakegpsdetector.utils.ResultUtils.showOnScreen

@Composable
fun InfoScreen(viewModel: InfoScreenViewModel = hiltViewModel()) {

    val infoScreenData = viewModel.infoScreenData.collectAsState()

    val apiResponse = viewModel.apiResponse.collectAsState()

    infoScreenData.showOnScreen {

        ShowData(
            infoScreenData = it,
            apiResponse = apiResponse.value
        )

    }

}

@Composable
fun ShowData(
    infoScreenData: InfoScreenData,
    apiResponse: ApiResponseModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(start = 6.dp)
    ) {
        ManageApiResponse(
            currentLocation = infoScreenData.currentLocationData,
            apiResponse = apiResponse
        )
    }
}

@Composable
fun ManageApiResponse(currentLocation: LocationModel, apiResponse: ApiResponseModel) {
    val distanceCalculator = DistanceCalculator(currentLocation, apiResponse.location)
    val result = distanceCalculator.isRealLocation(apiResponse.accuracy ?: 0.0)
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
