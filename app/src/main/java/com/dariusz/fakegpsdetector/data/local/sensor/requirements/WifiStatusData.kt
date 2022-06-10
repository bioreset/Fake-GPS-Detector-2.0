package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.net.wifi.WifiManager
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface WifiStatusData {

    val currentWifiStatus: Flow<WifiStatusModel>

}

class WifiStatusDataImpl
@Inject
constructor(
    @ApplicationContext private val context: Context
) : WifiStatusData {

    override val currentWifiStatus: Flow<WifiStatusModel> = context.liveWifiStatus()

    private fun Context.liveWifiStatus(): Flow<WifiStatusModel> {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as? WifiManager
        return flow { emit(getWifiStatus(wifiManager)) }
    }

    private fun getWifiStatus(wifiManager: WifiManager?) =
        if (wifiManager != null)
            isWifiEnabled(wifiManager.isWifiEnabled)
        else
            WifiStatusModel(status = false)

    private fun isWifiEnabled(status: Boolean): WifiStatusModel {
        return if (status)
            WifiStatusModel(status = true)
        else
            WifiStatusModel(status = false)
    }
}
