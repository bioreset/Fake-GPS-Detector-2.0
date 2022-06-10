package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import android.net.wifi.ScanResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.domain.model.Result
import com.dariusz.fakegpsdetector.domain.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.utils.ResultUtils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouterScreenViewModel
@Inject
constructor(
    private val wifiNodesRepository: WifiNodesRepository
) : ViewModel() {

    lateinit var wifiNodes: StateFlow<Result<List<ScanResult>>>

    init {
        viewModelScope.launch { getWifiNodesData() }
    }

    private suspend fun getWifiNodesData() {
        wifiNodes = wifiNodesRepository
            .getWifiNodes()
            .asResult(viewModelScope)
    }

}
