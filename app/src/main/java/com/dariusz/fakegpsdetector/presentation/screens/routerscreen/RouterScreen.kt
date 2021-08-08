package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.presentation.components.common.CardWifiNodes
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RouterScreen(routerScreenViewModel: RouterScreenViewModel) {
    val wifiNodesState by remember(routerScreenViewModel) { routerScreenViewModel.wifiNodes }.collectAsState()
    ManageResultOnScreen(wifiNodesState) {
        CardWifiNodes(newRoutersList(it))
    }
}
