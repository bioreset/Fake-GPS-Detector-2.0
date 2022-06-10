package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.domain.model.*
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.domain.repository.CellTowersDataRepository
import com.dariusz.fakegpsdetector.domain.repository.LocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.domain.repository.LocationRepository
import com.dariusz.fakegpsdetector.domain.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.ResultUtils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoScreenViewModel
@Inject
constructor(
    private val locationFromApiResponseRepository: LocationFromApiResponseRepository,
    private val locationRepository: LocationRepository,
    private val wifiNodesRepository: WifiNodesRepository,
    private val cellTowersRepository: CellTowersDataRepository
) : ViewModel() {

    lateinit var currentLocation: StateFlow<LocationModel>

    lateinit var currentRouters: StateFlow<List<RoutersListModel>>

    lateinit var currentCellTowers: StateFlow<List<CellTowerModel>>

    var apiResponse: StateFlow<ApiResponseModel>

    init {
        apiResponse = MutableStateFlow(ApiResponseModel(LocationData(0.0, 0.0), 0.0)).asStateFlow()
        viewModelScope.launch {
            getLocationData()
            getCellTowersData()
            getWifiNodesData()
            submitRequest()
        }
    }

    val infoScreenData: StateFlow<Result<InfoScreenData>> = combine(
        currentLocation,
        currentCellTowers,
        currentRouters
    ) { location, cellTowers, routers ->
        InfoScreenData(location, cellTowers, routers)
    }
        .asResult(viewModelScope)


    private suspend fun getLocationData() {
        currentLocation = locationRepository
            .getLocationData()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LocationModel(0.0, 0.0)
            )
    }

    @SuppressLint("NewApi")
    private suspend fun getCellTowersData() {
        currentCellTowers = cellTowersRepository
            .getCellTowers(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            .map { mapCellTowers(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf()
            )
    }

    @SuppressLint("NewApi")
    private suspend fun getWifiNodesData() {
        currentRouters = wifiNodesRepository
            .getWifiNodes()
            .map { newRoutersList(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = listOf()
            )
    }

    suspend fun submitRequest(
        cellTowers: List<CellTowerModel> = currentCellTowers.value,
        wifiNodes: List<RoutersListModel> = currentRouters.value
    ) {
        apiResponse = locationFromApiResponseRepository
            .checkCurrentLocationOfTheDevice(ApiRequestModel(wifiNodes, cellTowers))
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ApiResponseModel(LocationData(0.0, 0.0), 0.0)
            )
    }
}

data class InfoScreenData(
    val currentLocationData: LocationModel,
    val cellTowers: List<CellTowerModel>,
    val wifiNodes: List<RoutersListModel>
)