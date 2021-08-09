package com.dariusz.fakegpsdetector.presentation

import androidx.lifecycle.ViewModel
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.domain.model.ResultState
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import com.dariusz.fakegpsdetector.domain.repository.RequirementsRepository
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.launchVMTask
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.manageResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val requirementsRepository: RequirementsRepository
) : ViewModel() {

    private var _gpsStatus = MutableStateFlow<ResultState<GpsStatusModel>>(ResultState.Idle)
    val gpsStatus: StateFlow<ResultState<GpsStatusModel>> = _gpsStatus

    private var _permissionsStatus =
        MutableStateFlow<ResultState<PermissionStatusModel>>(ResultState.Idle)
    val permissionsStatus: StateFlow<ResultState<PermissionStatusModel>> = _permissionsStatus

    private var _wifiStatus = MutableStateFlow<ResultState<WifiStatusModel>>(ResultState.Idle)
    val wifiStatus: StateFlow<ResultState<WifiStatusModel>> = _wifiStatus

    fun getGPSStatus() = launchVMTask {
        manageResult(
            _gpsStatus,
            requirementsRepository.getGpsStatus()
        )

    }

    fun getPermissionsStatus() = launchVMTask {
        manageResult(
            _permissionsStatus,
            requirementsRepository.getPermissionsStatus()
        )
    }

    fun getWifiNetworkStatus() = launchVMTask {
        manageResult(
            _wifiStatus,
            requirementsRepository.getWifiStatus()
        )
    }
}
