package com.dariusz.fakegpsdetector.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import com.dariusz.fakegpsdetector.domain.repository.RequirementsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    requirementsRepository: RequirementsRepository
) : ViewModel() {

    private val gpsStatus = requirementsRepository.getGpsStatus()

    private val permissionsStatus = requirementsRepository.getPermissionsStatus()

    private val wifiNetworkStatus = requirementsRepository.getWifiStatus()

    val requirementsStatus: StateFlow<RequirementsStatus> = combine(
        gpsStatus,
        permissionsStatus,
        wifiNetworkStatus
    ) { gps, perm, wifi ->
        RequirementsStatus(gps, perm, wifi)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RequirementsStatus()
    )

}

data class RequirementsStatus(
    val gpsStatusModel: GpsStatusModel = GpsStatusModel(),
    val permissionStatusModel: PermissionStatusModel = PermissionStatusModel(),
    val wifiStatusModel: WifiStatusModel = WifiStatusModel()
)
