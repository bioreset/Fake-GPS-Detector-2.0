package com.dariusz.fakegpsdetector.data.local.sensor.wifinodes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WifiScanResultsData
@Inject
constructor(
    @ApplicationContext private val context: Context
) {

    fun getCurrentScanResults(): Flow<List<ScanResult>> =
        context.getCurrentScanResultsAsFlow()

    private fun Context.getCurrentScanResultsAsFlow(): Flow<List<ScanResult>> {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        return callbackFlow {
            val wifiScanReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    trySend(wifiManager.scanResults)
                }
            }
            registerReceiver(
                wifiScanReceiver,
                IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
            )
            awaitClose {
                unregisterReceiver(wifiScanReceiver)
            }
        }
    }
}
