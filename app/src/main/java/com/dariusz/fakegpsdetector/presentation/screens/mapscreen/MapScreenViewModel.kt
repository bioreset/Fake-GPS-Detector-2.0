package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.dariusz.fakegpsdetector.domain.model.Result
import com.dariusz.fakegpsdetector.domain.repository.LocationRepository
import com.dariusz.fakegpsdetector.utils.ResultUtils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
@Inject
constructor(
    locationRepository: LocationRepository
) : ViewModel() {

    val locationData: StateFlow<Result<LocationModel>> = locationRepository
        .getLocationData()
        .asResult(viewModelScope)

}