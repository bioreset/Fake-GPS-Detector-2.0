package com.dariusz.fakegpsdetector.ui.screens.thirdscreen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.data.repository.CellTowersRepository
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.CardCellTowers
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.Injectors.getCellTowersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun ThirdScreen(viewModel: ThirdScreenViewModel = ThirdScreenViewModel()) {
    val viewModelForNavigation: MainViewModel = viewModel()
    viewModelForNavigation.setCurrentScreen(Screens.AppScreens.ThirdScreen)
    val currentContext = LocalContext.current
    viewModel.getCellTowersDataLive(currentContext)
    val cellTowersState by remember(viewModel) { viewModel.cellTowers }.collectAsState()
    CardCellTowers(mapCellTowers(cellTowersState))
    LaunchedEffect(cellTowersState) {
        if (cellTowersState.isNotEmpty()) insertCellTowers(
            mapCellTowers(cellTowersState),
            currentContext
        )
    }
}


@InternalCoroutinesApi
private suspend fun insertCellTowers(cellTowers: List<CellTowerModel>, context: Context) =
    getCellTowersRepositoryLink(context).insertAsFresh(cellTowers)


@InternalCoroutinesApi
private fun getCellTowersRepositoryLink(context: Context): CellTowersRepository {
    return getCellTowersRepository(context)
}