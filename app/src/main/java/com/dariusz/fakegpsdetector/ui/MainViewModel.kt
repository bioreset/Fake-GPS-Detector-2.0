package com.dariusz.fakegpsdetector.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideGPSStatus
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.providePermissionsStatus
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideWifiStatus
import com.dariusz.fakegpsdetector.model.GpsStatusModel
import com.dariusz.fakegpsdetector.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.model.WifiStatusModel
import com.dariusz.fakegpsdetector.utils.Constants.permissionToWatch
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
class MainViewModel
@Inject
constructor(
) : ViewModel() {

    private var _gpsStatus = MutableStateFlow(GpsStatusModel(false))
    val gpsStatus: StateFlow<GpsStatusModel> = _gpsStatus

    private var _permissionsStatus = MutableStateFlow(PermissionStatusModel(false))
    val permissionsStatus: StateFlow<PermissionStatusModel> = _permissionsStatus

    private var _wifiStatus = MutableStateFlow(WifiStatusModel(false))
    val wifiStatus: StateFlow<WifiStatusModel> = _wifiStatus

    fun initViewModel(context: Context) {
        getGPSStatus(context)
        getPermissionsStatus(context, permissionToWatch)
        getWifiNetworkStatus(context)
    }

    private fun getGPSStatus(context: Context) =
        viewModelScope.launch {
            provideGPSStatus(context)
                .getLiveGPSStatus().collect { status ->
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
                .getLiveWifiStatus().collect { status ->
                    _wifiStatus.value = status
                }
        }
}
