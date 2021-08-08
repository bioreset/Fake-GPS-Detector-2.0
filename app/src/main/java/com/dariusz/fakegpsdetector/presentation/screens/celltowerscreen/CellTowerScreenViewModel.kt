package com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen

import android.telephony.CellInfo
import androidx.lifecycle.ViewModel
import com.dariusz.fakegpsdetector.domain.model.ResultState
import com.dariusz.fakegpsdetector.domain.repository.CellTowersDataRepository
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.launchVMTask
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.manageResultFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class CellTowerScreenViewModel
@Inject
constructor(
    private val cellTowersDataRepository: CellTowersDataRepository
) : ViewModel() {

    private var _cellTowers = MutableStateFlow<ResultState<List<CellInfo>>>(ResultState.Loading)
    val cellTowers: StateFlow<ResultState<List<CellInfo>>> = _cellTowers

    init {
        getCellTowersDataLive()
    }

    private fun getCellTowersDataLive() = launchVMTask {
        manageResultFlow(
            _cellTowers,
            cellTowersDataRepository.getCellTowers()
        )
    }

}
