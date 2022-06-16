package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.presentation.components.common.WifiNodeItemCard
import com.dariusz.fakegpsdetector.utils.ResultUtils.showOnScreen

@Composable
fun RouterScreen(viewModel: RouterScreenViewModel = hiltViewModel()) {

    val wifiNodes = viewModel.wifiNodes.collectAsState()

    wifiNodes.showOnScreen {
        LazyColumn {
            items(newRoutersList(it)) { wifiNode ->
                WifiNodeItemCard(wifiNode)
            }
        }
    }

}
