package com.dariusz.fakegpsdetector.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideGPSStatus
import com.dariusz.fakegpsdetector.di.DataSourceModule.providePermissionsStatus
import com.dariusz.fakegpsdetector.di.DataSourceModule.provideWifiStatus
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import com.dariusz.fakegpsdetector.utils.Constants.permissionToWatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
) : ViewModel() {

    private var _gpsStatus = MutableStateFlow(GpsStatusModel(false))
    val gpsStatus: StateFlow<GpsStatusModel> = _gpsStatus
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), GpsStatusModel(false))

    private var _permissionsStatus = MutableStateFlow(PermissionStatusModel(false))
    val permissionsStatus: StateFlow<PermissionStatusModel> = _permissionsStatus
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PermissionStatusModel(false))

    private var _wifiStatus = MutableStateFlow(WifiStatusModel(false))
    val wifiStatus: StateFlow<WifiStatusModel> = _wifiStatus
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), WifiStatusModel(false))

    fun initViewModel(context: Context) {
        getGPSStatus(context)
        getPermissionsStatus(context, permissionToWatch)
        getWifiNetworkStatus(context)
    }

    private fun getGPSStatus(context: Context) =
        viewModelScope.launch {
            provideGPSStatus(context)
                .currentGpsStatus
                .collect { status ->
                    _gpsStatus.value = status
                }
        }

    private fun getPermissionsStatus(
        context: Context,
        permissionsToListen: List<String>
    ) = viewModelScope.launch {
        providePermissionsStatus(context)
            .getLivePermissionStatus(permissionsToListen).collect { status ->
                _permissionsStatus.value = status
            }
    }

    private fun getWifiNetworkStatus(context: Context) =
        viewModelScope.launch {
            provideWifiStatus(context)
                .currentWifiStatus
                .collect { status ->
                    _wifiStatus.value = status
                }
        }
}
