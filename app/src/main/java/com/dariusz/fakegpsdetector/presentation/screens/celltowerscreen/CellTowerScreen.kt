package com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.dariusz.fakegpsdetector.presentation.components.common.CardCellTowers
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@SuppressLint("NewApi")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun CellTowerScreen(cellTowerScreenViewModel: CellTowerScreenViewModel) {
    val cellTowersState by remember(cellTowerScreenViewModel) { cellTowerScreenViewModel.cellTowers }.collectAsState()
    ManageResultOnScreen(cellTowersState) {
        CardCellTowers(mapCellTowers(it))
    }
}
