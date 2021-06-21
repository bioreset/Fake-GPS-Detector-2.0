package com.dariusz.fakegpsdetector.ui.screens.secondscreen

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.data.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.model.RoutersListModel
import com.dariusz.fakegpsdetector.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.CardWifiNodes
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import com.dariusz.fakegpsdetector.utils.Injectors.getRoutersListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun SecondScreen(viewModel: SecondScreenViewModel = SecondScreenViewModel()) {
    val viewModelForNavigation: MainViewModel = viewModel()
    viewModelForNavigation.setCurrentScreen(Screens.AppScreens.SecondScreen)
    val currentContext = LocalContext.current
    viewModel.getWifiNodesDataLive(currentContext)
    val wifiNodesState by remember(viewModel) { viewModel.wifiNodes }.collectAsState()
    CardWifiNodes(newRoutersList(wifiNodesState))
    LaunchedEffect(wifiNodesState) {
        if (wifiNodesState.isNotEmpty()) insertWifiNodes(
            newRoutersList(wifiNodesState),
            currentContext
        )
    }
}

@InternalCoroutinesApi
private suspend fun insertWifiNodes(wifiNodes: List<RoutersListModel>, context: Context) =
    getWifiNodesRepositoryLink(context).insertAsFresh(wifiNodes)

@InternalCoroutinesApi
private fun getWifiNodesRepositoryLink(context: Context): WifiNodesRepository {
    return getRoutersListRepository(context)
}