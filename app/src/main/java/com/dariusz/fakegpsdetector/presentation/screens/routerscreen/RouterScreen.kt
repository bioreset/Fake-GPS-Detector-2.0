package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import androidx.compose.runtime.Composable
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideWifiNodesRepository
import com.dariusz.fakegpsdetector.domain.model.RoutersListModel.Companion.newRoutersList
import com.dariusz.fakegpsdetector.presentation.components.common.CardWifiNodes
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import com.dariusz.fakegpsdetector.utils.ScreenUtlls.ShowScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun RouterScreen() {
    ShowScreen(
        viewModel = { context ->
            RouterScreenViewModel(
                provideWifiNodesRepository(context)
            )
        },
        data = { viewModel ->
            viewModel.wifiNodes
        },
        composable = { resultState ->
            ManageResultOnScreen(resultState) { result ->
                CardWifiNodes(newRoutersList(result))
            }
        },
        launchedEffect = { viewModel ->
            viewModel.getWifiNodesDataLive()
        }
    )
}
