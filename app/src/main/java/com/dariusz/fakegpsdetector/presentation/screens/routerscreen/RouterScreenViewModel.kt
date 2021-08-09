package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import android.net.wifi.ScanResult
import androidx.lifecycle.ViewModel
import com.dariusz.fakegpsdetector.domain.model.ResultState
import com.dariusz.fakegpsdetector.domain.repository.WifiNodesRepository
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.collectState
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.launchVMTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class RouterScreenViewModel
@Inject
constructor(
    private val wifiNodesRepository: WifiNodesRepository
) : ViewModel() {

    private var _wifiNodes = MutableStateFlow<ResultState<List<ScanResult>>>(ResultState.Idle)
    val wifiNodes: StateFlow<ResultState<List<ScanResult>>> = _wifiNodes

    fun getWifiNodesDataLive() = launchVMTask {
        wifiNodesRepository
            .getWifiNodes()
            .collectState(_wifiNodes)
    }
}
