package com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dariusz.fakegpsdetector.presentation.components.common.CellTowerItemCard
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.ResultUtils.showOnScreen

@Composable
fun CellTowerScreen(viewModel: CellTowerScreenViewModel = hiltViewModel()) {

    val cellTowers = viewModel.cellTowers.collectAsState()

    cellTowers.showOnScreen {
        LazyColumn {
            items(mapCellTowers(it)) { cellTowerItem ->
                CellTowerItemCard(cellTowerItem)
            }
        }
    }
}
