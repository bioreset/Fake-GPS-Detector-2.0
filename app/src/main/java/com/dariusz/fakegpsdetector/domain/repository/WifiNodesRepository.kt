package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.wifinodes.WifiScanResultsData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WifiNodesRepository
@Inject constructor(
    private val wifiScanResultsData: WifiScanResultsData
) {

    fun getWifiNodes() = wifiScanResultsData.getCurrentScanResults()

}