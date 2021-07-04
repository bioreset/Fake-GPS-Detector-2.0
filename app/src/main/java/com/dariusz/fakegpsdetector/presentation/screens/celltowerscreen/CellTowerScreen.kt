package com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.presentation.components.common.CardCellTowers
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun CellTowerScreen(cellTowerScreenViewModel: CellTowerScreenViewModel = viewModel()) {
    val currentContext = LocalContext.current
    if (Build.VERSION.SDK_INT < 29) {
        cellTowerScreenViewModel.getCellTowersDataLive(currentContext)
    } else {
        cellTowerScreenViewModel.getCellTowersDataLiveApi29Plus(currentContext)
    }
    val cellTowersState by remember(cellTowerScreenViewModel) { cellTowerScreenViewModel.cellTowers }.collectAsState()
    CardCellTowers(mapCellTowers(cellTowersState))
}
