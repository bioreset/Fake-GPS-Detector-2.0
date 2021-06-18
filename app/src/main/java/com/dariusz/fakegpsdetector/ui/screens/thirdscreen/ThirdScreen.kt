package com.dariusz.fakegpsdetector.ui.screens.thirdscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.common.CardCellTowers
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
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
}
