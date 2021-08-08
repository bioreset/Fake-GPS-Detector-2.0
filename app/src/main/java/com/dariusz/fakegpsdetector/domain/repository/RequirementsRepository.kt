package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.requirements.GpsStatusData
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.PermissionsStatusData
import com.dariusz.fakegpsdetector.data.local.sensor.requirements.WifiStatusData
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import com.dariusz.fakegpsdetector.utils.Constants.permissionsToWatch
import javax.inject.Inject

interface RequirementsRepository {

    suspend fun getGpsStatus(): GpsStatusModel

    suspend fun getPermissionsStatus(): PermissionStatusModel

    suspend fun getWifiStatus(): WifiStatusModel

}


class RequirementsRepositoryImpl
@Inject constructor(
    private val gpsStatusData: GpsStatusData,
    private val permissionsStatusData: PermissionsStatusData,
    private val wifiStatusData: WifiStatusData
) : RequirementsRepository {


    override suspend fun getGpsStatus() = gpsStatusData.currentGpsStatus

    override suspend fun getPermissionsStatus() = permissionsStatusData.getLivePermissionStatus(
        permissionsToWatch
    )

    override suspend fun getWifiStatus() = wifiStatusData.currentWifiStatus

}