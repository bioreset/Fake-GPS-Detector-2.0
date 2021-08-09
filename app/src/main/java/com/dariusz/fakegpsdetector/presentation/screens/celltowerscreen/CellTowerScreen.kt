package com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen

import android.os.Build
import androidx.compose.runtime.Composable
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideCellTowersDataRepository
import com.dariusz.fakegpsdetector.presentation.components.common.CardCellTowers
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import com.dariusz.fakegpsdetector.utils.ScreenUtlls.ShowScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun CellTowerScreen() {

    val newApi = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    ShowScreen(
        viewModel = { context ->
            CellTowerScreenViewModel(
                provideCellTowersDataRepository(context)
            )
        },
        data = {
            it.cellTowers
        },
        composable = { resultState ->
            ManageResultOnScreen(resultState) {
                CardCellTowers(mapCellTowers(it))
            }
        },
        launchedEffect = {
            it.getCellTowersDataLive(newApi)
        }
    )
}
