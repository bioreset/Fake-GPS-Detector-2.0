package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.presentation.components.common.CardWifiNodes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RouterScreen(viewModel: RouterScreenViewModel = RouterScreenViewModel()) {
    val currentContext = LocalContext.current
    viewModel.getWifiNodesDataLive(currentContext)
    val wifiNodesState by remember(viewModel) { viewModel.wifiNodes }.collectAsState()
    CardWifiNodes(newRoutersList(wifiNodesState))
}