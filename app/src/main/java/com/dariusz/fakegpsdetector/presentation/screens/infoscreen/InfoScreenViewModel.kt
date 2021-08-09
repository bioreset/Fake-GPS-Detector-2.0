package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.dariusz.fakegpsdetector.domain.model.*
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.domain.repository.CellTowersDataRepository
import com.dariusz.fakegpsdetector.domain.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.domain.repository.LocationRepository
import com.dariusz.fakegpsdetector.domain.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.collectState
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.launchVMTask
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.manageResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class InfoScreenViewModel
@Inject
constructor(
    private val locationFromApiResponseRepository: LocationFromApiResponseRepository,
    private val locationRepository: LocationRepository,
    private val wifiNodesRepository: WifiNodesRepository,
    private val cellTowersRepository: CellTowersDataRepository
) : ViewModel() {

    private var _currentLocation = MutableStateFlow<ResultState<LocationModel>>(ResultState.Idle)
    val currentLocation: StateFlow<ResultState<LocationModel>> = _currentLocation

    private var _currentRouters =
        MutableStateFlow<ResultState<List<RoutersListModel>>>(ResultState.Idle)
    val currentRouters: StateFlow<ResultState<List<RoutersListModel>>> = _currentRouters

    private var _currentCellTowers =
        MutableStateFlow<ResultState<List<CellTowerModel>>>(ResultState.Idle)
    val currentCellTowers: StateFlow<ResultState<List<CellTowerModel>>> = _currentCellTowers

    private var _apiResponse = MutableStateFlow<ResultState<ApiResponseModel>>(ResultState.Idle)
    val apiResponse: StateFlow<ResultState<ApiResponseModel>> = _apiResponse

    fun getLocationDataOnce() = launchVMTask {
        manageResult(
            _currentLocation,
            locationRepository.getLocationDataOnce()
        )
    }

    @SuppressLint("NewApi")
    fun getCellTowersData(newApi: Boolean) = launchVMTask {
        cellTowersRepository
            .getCellTowers(newApi)
            .map { mapCellTowers(it) }
            .collectState(_currentCellTowers)
    }

    @SuppressLint("NewApi")
    fun getWifiNodesData() = launchVMTask {
        wifiNodesRepository
            .getWifiNodes()
            .map { newRoutersList(it) }
            .collectState(_currentRouters)
    }

    @InternalCoroutinesApi
    fun submitRequest(
        cellTowers: List<CellTowerModel>,
        wifiNodes: List<RoutersListModel>
    ) = launchVMTask {
        manageResult(
            _apiResponse,
            locationFromApiResponseRepository
                .checkCurrentLocationOfTheDevice(
                    ApiRequestModel(wifiNodes, cellTowers)
                )
        )
    }
}
