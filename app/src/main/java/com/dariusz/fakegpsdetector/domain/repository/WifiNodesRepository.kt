package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.wifinodes.WifiScanResultsData
import javax.inject.Inject

class WifiNodesRepository
@Inject constructor(
    private val wifiScanResultsData: WifiScanResultsData
) {

    suspend fun getWifiNodes() = wifiScanResultsData.getCurrentScanResults()

}