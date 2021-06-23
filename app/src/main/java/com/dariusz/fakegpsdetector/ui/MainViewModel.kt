package com.dariusz.fakegpsdetector.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideGPSStatus
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.providePermissionsStatus
import com.dariusz.fakegpsdetector.di.NewDataSourceModule.provideWifiStatus
import com.dariusz.fakegpsdetector.model.GpsStatusModel
import com.dariusz.fakegpsdetector.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.model.WifiStatusModel
import com.dariusz.fakegpsdetector.ui.components.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
@ExperimentalCoroutinesApi
class MainViewModel
@Inject
constructor() : ViewModel() {

    private val _currentScreen = MutableLiveData<Screens>(Screens.AppScreens.MapScreen)
    val currentScreen: LiveData<Screens> = _currentScreen

    fun setCurrentScreen(screen: Screens) {
        _currentScreen.value = screen
    }

    private var _gpsStatus = MutableStateFlow(GpsStatusModel(false))
    val gpsStatus: StateFlow<GpsStatusModel> = _gpsStatus

    private var _permissionsStatus = MutableStateFlow(PermissionStatusModel(false))
    val permissionsStatus: StateFlow<PermissionStatusModel> = _permissionsStatus

    private var _wifiStatus = MutableStateFlow(WifiStatusModel(false))
    val wifiStatus: StateFlow<WifiStatusModel> = _wifiStatus

    fun getGPSStatus(context: Context) =
        viewModelScope.launch {
            provideGPSStatus(context)
                .getLiveGPSStatus().collect { status ->
                    _gpsStatus.value = status
                }
        }

    fun getPermissionsStatus(
        context: Context,
        permissionsToListen: List<String>
    ) = viewModelScope.launch {
        providePermissionsStatus(context)
            .getLivePermissionStatus(permissionsToListen).collect { status ->
                _permissionsStatus.value = status
            }
    }

    fun getWifiNetworkStatus(context: Context) =
        viewModelScope.launch {
            provideWifiStatus(context)
                .getLiveWifiStatus().collect { status ->
                    _wifiStatus.value = status
                }
        }
}
