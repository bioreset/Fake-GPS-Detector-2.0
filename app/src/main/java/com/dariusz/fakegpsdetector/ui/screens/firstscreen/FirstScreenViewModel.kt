package com.dariusz.fakegpsdetector.ui.screens.firstscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.data.repository.LocationRepository
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideLocationData
import com.dariusz.fakegpsdetector.model.LocationModel
import com.dariusz.fakegpsdetector.utils.Injectors.getLocationRepository
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
class FirstScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _locationData = MutableStateFlow(LocationModel(0.0, 0.0))
    val locationData: StateFlow<LocationModel> = _locationData

    fun getLocationLive(context: Context) =
        viewModelScope.launch {
            provideLocationData(context)
                .getCurrentLocationLive().collect { location ->
                    _locationData.value = location
                    insertLocation(location, context)
                }
        }

    private fun insertLocation(currentLocation: LocationModel, context: Context) =
        viewModelScope.launch {
            getLocationRepositoryLink(context).insertAsFresh(currentLocation)
        }

    private fun getLocationRepositoryLink(context: Context): LocationRepository {
        return getLocationRepository(context)
    }
}
