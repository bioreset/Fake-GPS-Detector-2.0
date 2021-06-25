package com.dariusz.fakegpsdetector.ui.screens.celltowerscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.dariusz.fakegpsdetector.ui.components.common.CardCellTowers
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun CellTowerScreen(viewModel: CellTowerScreenViewModel = CellTowerScreenViewModel()) {
    val currentContext = LocalContext.current
    if (Build.VERSION.SDK_INT < 29) {
        viewModel.getCellTowersDataLive(currentContext)
    } else {
        viewModel.getCellTowersDataLiveApi29Plus(currentContext)
    }
    val cellTowersState by remember(viewModel) { viewModel.cellTowers }.collectAsState()
    CardCellTowers(mapCellTowers(cellTowersState))
}
