package com.dariusz.fakegpsdetector.ui.screens.secondscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.CardWifiNodes
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
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
}
