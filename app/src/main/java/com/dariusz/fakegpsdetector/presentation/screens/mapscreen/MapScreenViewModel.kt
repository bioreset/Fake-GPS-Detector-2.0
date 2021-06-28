package com.dariusz.fakegpsdetector.presentation.screens.mapscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideLocationData
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class MapScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _locationData = MutableStateFlow(LocationModel(0.0, 0.0))
    val locationData: StateFlow<LocationModel> = _locationData

    fun getLocationLive(context: Context) =
        viewModelScope.launch {
            provideLocationData(context)
                .getCurrentLocationLive().collect { location ->
                    _locationData.value = location
                }
        }

}
