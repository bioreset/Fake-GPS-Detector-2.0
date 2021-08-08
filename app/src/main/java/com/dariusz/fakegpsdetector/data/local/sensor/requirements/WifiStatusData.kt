package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.net.wifi.WifiManager
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

interface WifiStatusData {

    val currentWifiStatus: WifiStatusModel

}

@ExperimentalCoroutinesApi
class WifiStatusDataImpl
@Inject
constructor(
    context: Context
) : WifiStatusData {

    override val currentWifiStatus: WifiStatusModel = context.liveWifiStatus()

    private fun Context.liveWifiStatus(): WifiStatusModel {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as? WifiManager
        return getWifiStatus(wifiManager!!)
    }

    private fun getWifiStatus(wifiManager: WifiManager) = isWifiEnabled(wifiManager.isWifiEnabled)

    private fun isWifiEnabled(status: Boolean): WifiStatusModel {
        return if (status)
            WifiStatusModel(status = true)
        else
            WifiStatusModel(status = false)
    }
}
