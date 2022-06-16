package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class InfoScreenViewModel
@Inject
constructor(
    locationFromApiResponseRepository: LocationFromApiResponseRepository,
    locationRepository: LocationRepository,
    wifiNodesRepository: WifiNodesRepository,
    cellTowersRepository: CellTowersDataRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val infoScreenState: StateFlow<Result<InfoScreenState>> = combine(
        wifiNodesRepository.getWifiNodes(),
        cellTowersRepository.getCellTowers(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q),
    ) { wifiNodes, cellTowers ->
        val cellTowersList = mapCellTowers(cellTowers)
        val wifiNodesList = newRoutersList(wifiNodes)
        locationFromApiResponseRepository.checkCurrentLocationOfTheDevice(
            ApiRequestModel(wifiNodesList, cellTowersList)
        )
    }
        .flatMapLatest { it }
        .combine(locationRepository.getLocationData()){ apiResponse, location ->
            InfoScreenState(
                currentLocationData = location,
                apiResponseModel = apiResponse
            )
        }
        .asResult(viewModelScope)

}

data class InfoScreenState(
    val currentLocationData: LocationModel? = null,
    val apiResponseModel: ApiResponseModel? = null
)