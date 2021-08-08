package com.dariusz.fakegpsdetector.domain.repository

import android.net.wifi.ScanResult
import com.dariusz.fakegpsdetector.data.local.sensor.wifinodes.WifiScanResultsData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WifiNodesRepository {

    suspend fun getWifiNodes(): Flow<List<ScanResult>>

}

class WifiNodesRepositoryImpl
@Inject constructor(
    private val wifiScanResultsData: WifiScanResultsData
) : WifiNodesRepository {

    override suspend fun getWifiNodes() = wifiScanResultsData.getCurrentScanResultsLive()

}