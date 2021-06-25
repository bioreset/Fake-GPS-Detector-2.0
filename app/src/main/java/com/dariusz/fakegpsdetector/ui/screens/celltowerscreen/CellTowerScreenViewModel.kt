package com.dariusz.fakegpsdetector.ui.screens.celltowerscreen

import android.content.Context
import android.os.Build
import android.telephony.CellInfo
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideCellTowersData
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideCellTowersDataApi29Plus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class CellTowerScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _cellTowers = MutableStateFlow(listOf<CellInfo>())
    val cellTowers: StateFlow<List<CellInfo>> = _cellTowers

    @RequiresApi(Build.VERSION_CODES.P)
    fun getCellTowersDataLive(context: Context) =
        viewModelScope.launch {
            provideCellTowersData(context).getCurrentCellTowersLive().collect { list ->
                _cellTowers.value = list
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getCellTowersDataLiveApi29Plus(context: Context) =
        viewModelScope.launch {
            provideCellTowersDataApi29Plus(context).getCurrentCellTowersLive().collect { list ->
                _cellTowers.value = list
            }
        }

}
