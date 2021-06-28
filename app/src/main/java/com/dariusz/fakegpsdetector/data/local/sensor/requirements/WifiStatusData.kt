package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class WifiStatusData
@Inject
constructor(
    private val context: Context
) {

    fun getLiveWifiStatus() = context.liveWifiStatus()

    private fun Context.liveWifiStatus(): Flow<WifiStatusModel> {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as? WifiManager
        val wifiStatus: WifiStatusModel = getWifiStatus(wifiManager!!)
        return callbackFlow {
            val wifiScanReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    if (intent.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
                        unregisterReceiver(this)
                        this@callbackFlow.trySend(wifiStatus).isSuccess
                    }
                }
            }
            registerReceiver(wifiScanReceiver, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
            awaitClose {
                unregisterReceiver(wifiScanReceiver)
            }
        }.shareIn(
            MainScope(),
            SharingStarted.WhileSubscribed()
        )
    }

    private fun getWifiStatus(wifiManager: WifiManager) = isWifiEnabled(wifiManager.isWifiEnabled)

    private fun isWifiEnabled(status: Boolean): WifiStatusModel {
        return if (status)
            WifiStatusModel(status = true)
        else
            WifiStatusModel(status = false)
    }
}
