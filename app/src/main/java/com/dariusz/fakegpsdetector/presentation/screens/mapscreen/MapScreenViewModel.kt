package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import androidx.lifecycle.ViewModel
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.dariusz.fakegpsdetector.domain.model.ResultState
import com.dariusz.fakegpsdetector.domain.repository.LocationRepository
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.collectState
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.launchVMTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class MapScreenViewModel
@Inject
constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private var _locationData = MutableStateFlow<ResultState<LocationModel>>(ResultState.Idle)
    val locationData: StateFlow<ResultState<LocationModel>> = _locationData

    fun getLocationLive() = launchVMTask {
        locationRepository
            .getLocationDataLive()
            .collectState(_locationData)
    }


}
