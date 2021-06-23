package com.dariusz.fakegpsdetector.ui.screens.infoscreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.data.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideCellTowersData
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideLocationData
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideWifiScanResults
import com.dariusz.fakegpsdetector.model.ApiResponseModel
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.model.LocationModel
import com.dariusz.fakegpsdetector.model.RoutersListModel
import com.dariusz.fakegpsdetector.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.Injectors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private var _apiResponse = MutableStateFlow(ApiResponseModel(null, null, null, null))
    val apiResponse: StateFlow<ApiResponseModel> = _apiResponse

    fun initScreenTasks(context: Context) {
        getLocationDataOnce(context)
        getCellTowersDataOnce(context)
        getWifiNodesDataOnce(context)
    }

    fun getLocationFromApisResponse(context: Context) = viewModelScope.launch {
        _apiResponse.value = getLocationFromApiResponseRepositoryLink(context).checkLocationStatus()
    }

    fun manageTheResponse(
        context: Context,
        cells: List<CellTowerModel>,
        wifis: List<RoutersListModel>
    ) =
        viewModelScope.launch {
            getLocationFromApiResponseRepositoryLink(context).manageResponseAfterAction(
                cells,
                wifis,
                context
            )
        }

    private fun getLocationDataOnce(context: Context) = viewModelScope.launch {
        _currentLocation.value = provideLocationData(context).getCurrentLocationOnce()
    }

    private fun getCellTowersDataOnce(context: Context) = viewModelScope.launch {
        val provideData = provideCellTowersData(context)
        val cellInfoList = provideData.getCurrentCellTowersOnce()
        _currentCellTowers.value = mapCellTowers(cellInfoList)
    }

    private fun getWifiNodesDataOnce(context: Context) = viewModelScope.launch {
        val provideData = provideWifiScanResults(context)
        val scanResultList = provideData.getCurrentScanResultsOnce()
        _currentRouters.value = newRoutersList(scanResultList)
    }

    private fun getLocationFromApiResponseRepositoryLink(context: Context): LocationFromApiResponseRepository {
        return Injectors.getLocationFromApiResponseRepository(context)
    }

}
