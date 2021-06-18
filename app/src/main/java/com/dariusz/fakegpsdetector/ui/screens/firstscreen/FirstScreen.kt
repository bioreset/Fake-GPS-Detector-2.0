package com.dariusz.fakegpsdetector.ui.screens.firstscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.CityMapView
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun FirstScreen(viewModel: FirstScreenViewModel = FirstScreenViewModel()) {
    val viewModelForNavigation: MainViewModel = viewModel()
    viewModelForNavigation.setCurrentScreen(Screens.AppScreens.FirstScreen)
    val context = LocalContext.current
    viewModel.getLocationLive(context)
    val locationState by remember(viewModel) { viewModel.locationData }.collectAsState()
    CityMapView(locationState.latitude.toString(), locationState.longitude.toString())
}
