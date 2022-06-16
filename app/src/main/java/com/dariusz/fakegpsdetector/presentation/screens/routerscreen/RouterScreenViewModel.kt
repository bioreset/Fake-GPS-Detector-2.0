package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import android.net.wifi.ScanResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.domain.model.Result
import com.dariusz.fakegpsdetector.domain.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.utils.ResultUtils.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RouterScreenViewModel
@Inject
constructor(
    wifiNodesRepository: WifiNodesRepository
) : ViewModel() {

    val wifiNodes: StateFlow<Result<List<ScanResult>>> = wifiNodesRepository
        .getWifiNodes()
        .asResult(viewModelScope)

}