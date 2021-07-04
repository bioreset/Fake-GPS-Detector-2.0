package com.dariusz.fakegpsdetector.presentation.screens.routerscreen

import android.content.Context
import android.net.wifi.ScanResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideWifiScanResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class RouterScreenViewModel
@Inject
constructor() : ViewModel() {

    private var _wifiNodes = MutableStateFlow(listOf<ScanResult>())
    val wifiNodes: StateFlow<List<ScanResult>> = _wifiNodes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    fun getWifiNodesDataLive(context: Context) =
        viewModelScope.launch {
            provideWifiScanResults(context).getCurrentScanResultsLive().collect { list ->
                _wifiNodes.value = list
            }
        }

}
