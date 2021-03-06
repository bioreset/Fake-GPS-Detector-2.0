package com.dariusz.fakegpsdetector.presentation.screens.celltowerscreen

import android.os.Build
import android.telephony.CellInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.domain.model.Result
import com.dariusz.fakegpsdetector.domain.repository.CellTowersDataRepository
import com.dariusz.fakegpsdetector.utils.ResultUtils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CellTowerScreenViewModel
@Inject
constructor(
    cellTowersDataRepository: CellTowersDataRepository
) : ViewModel() {

    val cellTowers: StateFlow<Result<List<CellInfo>>> = cellTowersDataRepository
        .getCellTowers(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        .asResult(viewModelScope)

}
