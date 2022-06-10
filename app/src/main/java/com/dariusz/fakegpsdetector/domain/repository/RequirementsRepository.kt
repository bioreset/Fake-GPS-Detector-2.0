package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.requirements.GpsStatusData
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.PermissionsStatusData
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.WifiStatusData
import com.dariusz.fakegpsdetector.utils.Constants.permissionsToWatch
import javax.inject.Inject

class RequirementsRepository
@Inject constructor(
    private val gpsStatusData: GpsStatusData,
    private val permissionsStatusData: PermissionsStatusData,
    private val wifiStatusData: WifiStatusData
) {

    fun getGpsStatus() = gpsStatusData.currentGpsStatus

    fun getPermissionsStatus() = permissionsStatusData.getPermissionStatus(
        permissionsToWatch
    )

    fun getWifiStatus() = wifiStatusData.currentWifiStatus

}