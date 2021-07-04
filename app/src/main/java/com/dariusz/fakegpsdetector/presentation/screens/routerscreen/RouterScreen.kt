package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.presentation.components.common.CardWifiNodes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RouterScreen(routerScreenViewModel: RouterScreenViewModel = viewModel()) {
    val currentContext = LocalContext.current
    routerScreenViewModel.getWifiNodesDataLive(currentContext)
    val wifiNodesState by remember(routerScreenViewModel) { routerScreenViewModel.wifiNodes }.collectAsState()
    CardWifiNodes(newRoutersList(wifiNodesState))
}
