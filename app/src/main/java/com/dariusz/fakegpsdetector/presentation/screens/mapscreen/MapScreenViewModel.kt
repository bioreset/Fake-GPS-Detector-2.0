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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapScreenViewModel
@Inject
constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    lateinit var locationData: StateFlow<Result<LocationModel>>

    init {
        viewModelScope.launch { getLocation() }
    }

    private suspend fun getLocation() {
        locationData = locationRepository
            .getLocationData()
            .asResult(viewModelScope)
    }


}
