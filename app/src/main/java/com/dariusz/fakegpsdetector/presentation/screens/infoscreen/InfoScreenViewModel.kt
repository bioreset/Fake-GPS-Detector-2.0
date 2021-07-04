package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideCellTowersData
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideCellTowersDataApi29Plus
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideLocationData
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideWifiScanResults
import com.dariusz.fakegpsdetector.domain.model.*
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.domain.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class InfoScreenViewModel
@Inject
constructor(
    private val locationFromApiResponseRepository: LocationFromApiResponseRepository
) : ViewModel() {

    private var _currentLocation = MutableStateFlow(LocationModel(0.0, 0.0))
    val currentLocation: StateFlow<LocationModel> = _currentLocation
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LocationModel(0.0, 0.0))

    private var _currentRouters = MutableStateFlow(listOf<RoutersListModel>())
    val currentRouters: StateFlow<List<RoutersListModel>> = _currentRouters
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private var _currentCellTowers = MutableStateFlow(listOf<CellTowerModel>())
    val currentCellTowers: StateFlow<List<CellTowerModel>> = _currentCellTowers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun initScreenTasks(context: Context) {
        getLocationDataOnce(context)
        getWifiNodesData(context)
    }

    private fun getLocationDataOnce(context: Context) = viewModelScope.launch {
        _currentLocation.value = provideLocationData(context).getCurrentLocationOnce()
    }

    fun getCellTowersData(context: Context) = viewModelScope.launch {
        provideCellTowersData(context)
            .getCurrentCellTowersLive()
            .collect { cellTowers ->
                _currentCellTowers.value = mapCellTowers(cellTowers)
            }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getCellTowersDataLiveApi29Plus(context: Context) =
        viewModelScope.launch {
            provideCellTowersDataApi29Plus(context)
                .getCurrentCellTowersLive()
                .collect { cellTowers ->
                    _currentCellTowers.value = mapCellTowers(cellTowers)
                }
        }

    private fun getWifiNodesData(context: Context) = viewModelScope.launch {
        provideWifiScanResults(context)
            .getCurrentScanResultsLive()
            .collect { wifiNodes ->
                _currentRouters.value = newRoutersList(wifiNodes)
            }
    }


    private var _apiResponse = MutableSharedFlow<ApiResponseModel>(replay = 1)
    val apiResponse: SharedFlow<ApiResponseModel> = _apiResponse
        .shareIn(
            viewModelScope,
            SharingStarted.Eagerly,
            1
        )


    @InternalCoroutinesApi
    fun submitRequest(
        cellTowers: List<CellTowerModel>,
        wifiNodes: List<RoutersListModel>
    ) = viewModelScope.launch {
        _apiResponse.emit(
            locationFromApiResponseRepository
                .checkCurrentLocationOfTheDevice(
                    ApiRequestModel(wifiNodes, cellTowers)
                )
        )

    }
}
