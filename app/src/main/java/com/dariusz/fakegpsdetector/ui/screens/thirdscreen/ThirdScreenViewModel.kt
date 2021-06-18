package com.dariusz.fakegpsdetector.ui.screens.thirdscreen

import android.content.Context
import android.os.Build
import android.telephony.CellInfo
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.data.repository.CellTowersRepository
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideCellTowersData
import com.dariusz.fakegpsdetector.model.CellTowerModel
import com.dariusz.fakegpsdetector.utils.CellTowersUtils.mapCellTowers
import com.dariusz.fakegpsdetector.utils.Injectors.getCellTowersRepository
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
class ThirdScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _cellTowers = MutableStateFlow(listOf<CellInfo>())
    val cellTowers: StateFlow<List<CellInfo>> = _cellTowers

    @RequiresApi(Build.VERSION_CODES.P)
    fun getCellTowersDataLive(context: Context) =
        viewModelScope.launch {
            provideCellTowersData(context).getCurrentCellTowersLive().collect { list ->
                _cellTowers.value = list
                if (list.isNotEmpty()) insertCellTowers(mapCellTowers(list), context)
            }
        }

    private fun insertCellTowers(cellTowers: List<CellTowerModel>, context: Context) =
        viewModelScope.launch {
            getCellTowersRepositoryLink(context).insertAsFresh(cellTowers)
        }

    private fun getCellTowersRepositoryLink(context: Context): CellTowersRepository {
        return getCellTowersRepository(context)
    }
}
