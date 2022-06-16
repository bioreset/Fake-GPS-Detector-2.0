package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
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
    cellTowersRepository: CellTowersDataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var apiRequest by savedStateHandle.saveable {
        mutableStateOf(ApiRequestModel(listOf(), listOf()))
    }

    val viewState: StateFlow<Result<InfoScreenViewState>> = combine(
        locationRepository.getLocationData(),
        wifiNodesRepository.getWifiNodes(),
        cellTowersRepository.getCellTowers(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q),
        snapshotFlow { apiRequest }
            .flatMapLatest {
                locationFromApiResponseRepository.checkCurrentLocationOfTheDevice(it)
            }
    ) { locationData, wifinodes, celltowers, apiResponse ->
        val cellTowersList = mapCellTowers(celltowers)
        val wifiNodesList = newRoutersList(wifinodes)
        InfoScreenViewState(
            currentLocationData = locationData,
            cellTowers = cellTowersList,
            wifiNodes = wifiNodesList,
            apiResponseModel = apiResponse
        )
    }
        .asResult(viewModelScope)

}

data class InfoScreenViewState(
    val currentLocationData: LocationModel? = null,
    val cellTowers: List<CellTowerModel>? = listOf(),
    val wifiNodes: List<RoutersListModel>? = listOf(),
    val apiResponseModel: ApiResponseModel? = null
)