package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.dariusz.fakegpsdetector.presentation.components.common.CityMapView
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MapScreen(mapScreenViewModel: MapScreenViewModel) {
    val locationState by remember(mapScreenViewModel) { mapScreenViewModel.locationData }.collectAsState()
    ManageResultOnScreen(locationState) {
        CityMapView(it.latitude.toString(), it.longitude.toString())
    }

}
