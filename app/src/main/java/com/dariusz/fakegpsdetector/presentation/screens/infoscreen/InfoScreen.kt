package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.dariusz.fakegpsdetector.presentation.components.common.BaseDetail
import com.dariusz.fakegpsdetector.utils.DistanceCalculator
import com.dariusz.fakegpsdetector.utils.DistanceCalculator.getFinalResult
import com.dariusz.fakegpsdetector.utils.ResultUtils.showOnScreen

@Composable
fun InfoScreen(viewModel: InfoScreenViewModel = hiltViewModel()) {

    val viewState = viewModel.infoScreenState.collectAsState()

    viewState.showOnScreen {
        ShowData(it)
    }

}

@Composable
private fun ShowData(
    state: InfoScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(start = 6.dp)
    ) {
        ManageApiResponse(
            currentLocation = state.currentLocationData,
            apiResponse = state.apiResponseModel
        )
    }
}

@Composable
private fun ManageApiResponse(
    currentLocation: LocationModel?,
    apiResponse: ApiResponseModel?
) {
    val result = getFinalResult(currentLocation, apiResponse?.location, apiResponse?.accuracy)
    val verdict = if (result.isSpoofed) "Valid" else "Spoofed"
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Current location: ", style = TextStyle(fontWeight = FontWeight.Bold))
        Text("Latitude: ${currentLocation?.latitude}, Longitude: ${currentLocation?.longitude}")
        Spacer(modifier = Modifier.height(15.dp))
        Text("Current location from API: ", style = TextStyle(fontWeight = FontWeight.Bold))
        Text("Latitude: ${apiResponse?.location?.lat}, Longitude: ${apiResponse?.location?.lng}")
        Spacer(modifier = Modifier.height(15.dp))
        Text("Verdict: ", style = TextStyle(fontWeight = FontWeight.Bold))
        Text(verdict)
        Spacer(modifier = Modifier.height(15.dp))
        Text("Distance: ", style = TextStyle(fontWeight = FontWeight.Bold))
        Text("about ${result.distance} meter(s)")
    }
}
