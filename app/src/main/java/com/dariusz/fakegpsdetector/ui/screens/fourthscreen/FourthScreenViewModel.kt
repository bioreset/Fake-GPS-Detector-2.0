package com.dariusz.fakegpsdetector.ui.screens.fourthscreen

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
import com.dariusz.fakegpsdetector.utils.Injectors.getLocationFromApiResponseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class FourthScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _apiResponse = MutableStateFlow(ApiResponseModel(null, null, null, null))
    val apiResponse: StateFlow<ApiResponseModel> = _apiResponse

    private var _currentLocation = MutableStateFlow(LocationModel(0.0, 0.0))
    val currentLocation: StateFlow<LocationModel> = _currentLocation

    fun getLocationFromApisResponse(context: Context) = viewModelScope.launch {
        prepareRequest(context)
        delay(2000)
        _apiResponse.value = getLocationFromApiResponseRepositoryLink(context).checkLocationStatus()
            ?: ApiResponseModel(null, null, null, null)
    }

    fun getLocationDataOnce(context: Context) = viewModelScope.launch {
        _currentLocation.value = provideLocationData(context).getCurrentLocationOnce()
    }

    private fun prepareRequest(context: Context) = viewModelScope.launch {
        getLocationFromApiResponseRepositoryLink(context).manageResponseAfterAction(
            getCellTowersDataOnce(context),
            getWifiNodesDataOnce(context),
            context
        )
    }

    private fun getLocationFromApiResponseRepositoryLink(context: Context): LocationFromApiResponseRepository {
        return getLocationFromApiResponseRepository(context)
    }

    private suspend fun getCellTowersDataOnce(context: Context): List<CellTowerModel> {
        val provideData = provideCellTowersData(context)
        val cellInfoList = provideData.getCurrentCellTowersOnce()
        return mapCellTowers(cellInfoList)
    }

    private suspend fun getWifiNodesDataOnce(context: Context): List<RoutersListModel> {
        val provideData = provideWifiScanResults(context)
        val scanResultList = provideData.getCurrentScanResultsOnce()
        return newRoutersList(scanResultList)
    }
}
