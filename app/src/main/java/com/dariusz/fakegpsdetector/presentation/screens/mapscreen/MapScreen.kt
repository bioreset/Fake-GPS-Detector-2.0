package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dariusz.fakegpsdetector.presentation.components.common.CityMapView
import com.dariusz.fakegpsdetector.utils.ResultUtils.showOnScreen

@Composable
fun MapScreen(viewModel: MapScreenViewModel = hiltViewModel()) {

    val locationData = viewModel.locationData.collectAsState()

    locationData.showOnScreen {
        CityMapView(
            it.latitude.toString(),
            it.longitude.toString()
        )
    }
}
