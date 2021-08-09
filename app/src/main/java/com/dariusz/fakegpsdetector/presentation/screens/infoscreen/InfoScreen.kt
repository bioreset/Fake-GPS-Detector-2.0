package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideCellTowersDataRepository
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideLocationFromApiResponseRepository
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideLocationRepository
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideWifiNodesRepository
import com.dariusz.fakegpsdetector.domain.model.*
import com.dariusz.fakegpsdetector.presentation.components.common.BaseDetail
import com.dariusz.fakegpsdetector.utils.DistanceCalculator
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultsOnScreen
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.composeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun InfoScreen() {

    val currentContext = LocalContext.current

    val infoScreenViewModel = composeViewModel {
        InfoScreenViewModel(
            provideLocationFromApiResponseRepository(),
            provideLocationRepository(currentContext),
            provideWifiNodesRepository(currentContext),
            provideCellTowersDataRepository(currentContext)
        )
    }

    val currentCoroutineScope = rememberCoroutineScope()
    val currentLocation by remember(infoScreenViewModel) { infoScreenViewModel.currentLocation }.collectAsState()
    val currentWifiRouters by remember(infoScreenViewModel) { infoScreenViewModel.currentRouters }.collectAsState()
    val currentCellTowers by remember(infoScreenViewModel) { infoScreenViewModel.currentCellTowers }.collectAsState()

    ManageResultsOnScreen(
        currentLocation,
        currentWifiRouters,
        currentCellTowers
    ) { location, wifi, cell ->
        if (wifi != null && cell != null) {
            ShowData(
                infoScreenViewModel = infoScreenViewModel,
                currentCoroutineScope = currentCoroutineScope,
                location = location,
                cells = cell,
                wifis = wifi
            )
        }
    }

    val newApi = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    LaunchedEffect(Unit) {
        infoScreenViewModel.apply {
            getLocationDataOnce()
            getCellTowersData(newApi)
            getWifiNodesData()
        }
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun ShowData(
    infoScreenViewModel: InfoScreenViewModel,
    currentCoroutineScope: CoroutineScope,
    location: LocationModel,
    cells: List<CellTowerModel>,
    wifis: List<RoutersListModel>
) {
    val apiRequestSent = remember { mutableStateOf(false) }
    val apiResponse = remember {
        mutableStateOf(ApiResponseModel(LocationData(0.0, 0.0), 0))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(start = 6.dp)
    ) {
        Button(
            onClick = {
                apiRequestSent.value = true
                infoScreenViewModel.submitRequest(cells, wifis)

                currentCoroutineScope.launch {
                    infoScreenViewModel.apiResponse.collect {
                        when (it) {
                            is ResultState.Success -> apiResponse.value = it.data
                        }
                    }
                }

            },
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Text("Submit")
        }
        if (apiRequestSent.value)
            ManageApiResponse(
                currentLocation = location,
                apiResponse = apiResponse.value
            )
    }
}

@Composable
fun ManageApiResponse(currentLocation: LocationModel, apiResponse: ApiResponseModel) {
    val distanceCalculator = DistanceCalculator(currentLocation, apiResponse.location)
    val result = distanceCalculator.isRealLocation(apiResponse.accuracy ?: 0)
    val verdict = if (result) "True" else "Spoofed"
    BaseDetail(
        "Current Location: ",
        "lat: ${currentLocation.latitude}, lng: ${currentLocation.longitude}",
    )
    BaseDetail(
        "Current Location From API: ",
        "lat: ${apiResponse.location.lat}, lng: ${apiResponse.location.lng}, accuracy: ${apiResponse.accuracy} "
    )
    BaseDetail("Verdict: ", verdict)
}
