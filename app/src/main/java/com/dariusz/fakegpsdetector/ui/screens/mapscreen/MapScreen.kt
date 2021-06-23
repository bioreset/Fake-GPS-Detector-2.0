package com.dariusz.fakegpsdetector.ui.screens.mapscreen

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.data.repository.LocationRepository
import com.dariusz.fakegpsdetector.model.LocationModel
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.CityMapView
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import com.dariusz.fakegpsdetector.utils.Injectors.getLocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MapScreen(viewModel: MapScreenViewModel = MapScreenViewModel()) {
    val viewModelForNavigation: MainViewModel = viewModel()
    viewModelForNavigation.setCurrentScreen(Screens.AppScreens.MapScreen)
    val currentContext = LocalContext.current
    viewModel.getLocationLive(currentContext)
    val locationState by remember(viewModel) { viewModel.locationData }.collectAsState()
    LaunchedEffect(locationState) {
        insertLocation(locationState, currentContext)
    }
    CityMapView(locationState.latitude.toString(), locationState.longitude.toString())
}


@InternalCoroutinesApi
suspend fun insertLocation(currentLocation: LocationModel, context: Context) =
    getLocationRepositoryLink(context).insertAsFresh(currentLocation)

@InternalCoroutinesApi
private fun getLocationRepositoryLink(context: Context): LocationRepository {
    return getLocationRepository(context)
}
