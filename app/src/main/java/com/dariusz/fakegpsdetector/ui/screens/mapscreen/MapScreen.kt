package com.dariusz.fakegpsdetector.ui.screens.mapscreen

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.dariusz.fakegpsdetector.ui.components.common.CityMapView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MapScreen(viewModel: MapScreenViewModel = MapScreenViewModel()) {
    val currentContext = LocalContext.current
    viewModel.getLocationLive(currentContext)
    val locationState by remember(viewModel) { viewModel.locationData }.collectAsState()
    CityMapView(locationState.latitude.toString(), locationState.longitude.toString())
}
