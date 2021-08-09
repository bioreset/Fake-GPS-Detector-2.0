package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import androidx.compose.runtime.Composable
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideLocationRepository
import com.dariusz.fakegpsdetector.presentation.components.common.CityMapView
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import com.dariusz.fakegpsdetector.utils.ScreenUtlls.ShowScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MapScreen() {
    ShowScreen(
        viewModel = { context ->
            MapScreenViewModel(
                provideLocationRepository(context)
            )
        },
        data = { viewModel ->
            viewModel.locationData
        },
        composable = { resultState ->
            ManageResultOnScreen(resultState) {
                CityMapView(it.latitude.toString(), it.longitude.toString())
            }
        },
        launchedEffect = { viewModel ->
            viewModel.getLocationLive()
        }
    )
}
