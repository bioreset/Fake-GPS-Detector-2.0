package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.presentation.components.common.CityMapView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MapScreen(mapScreenViewModel: MapScreenViewModel = viewModel()) {
    val currentContext = LocalContext.current
    mapScreenViewModel.getLocationLive(currentContext)
    val locationState by remember(mapScreenViewModel) { mapScreenViewModel.locationData }.collectAsState()
    CityMapView(locationState.latitude.toString(), locationState.longitude.toString())
}
