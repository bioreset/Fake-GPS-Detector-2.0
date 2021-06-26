package com.dariusz.fakegpsdetector.ui.screens.infoscreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.data.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.di.NewDataSourceModule
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideCellTowersData
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideCellTowersDataApi29Plus
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideLocationData
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideWifiScanResults
import com.dariusz.fakegpsdetector.model.*
import com.dariusz.fakegpsdetector.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.Injectors.getLocationFromApiResponseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class InfoScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _currentLocation = MutableStateFlow(LocationModel(0.0, 0.0))
    val currentLocation: StateFlow<LocationModel> = _currentLocation

    private var _currentRouters = MutableStateFlow(listOf<RoutersListModel>())
    val currentRouters: StateFlow<List<RoutersListModel>> = _currentRouters

    private var _currentCellTowers = MutableStateFlow(listOf<CellTowerModel>())
    val currentCellTowers: StateFlow<List<CellTowerModel>> = _currentCellTowers

    private var _apiResponse = MutableStateFlow(ApiResponseModel(LocationData(null, null), null))
    val apiResponse: StateFlow<ApiResponseModel> = _apiResponse

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


    @InternalCoroutinesApi
    fun submitRequest(
        context: Context,
        cellTowers: List<CellTowerModel>,
        wifiNodes: List<RoutersListModel>
    ) = viewModelScope.launch {
        _apiResponse.value = getLocationFromApiResponseRepositoryLink()
                    .checkCurrentLocationOfTheDevice(
                        ApiRequestModel(wifiNodes, cellTowers),
                        context
                    )
    }

    @InternalCoroutinesApi
    private fun getLocationFromApiResponseRepositoryLink(): LocationFromApiResponseRepository {
        return getLocationFromApiResponseRepository()
    }

}
