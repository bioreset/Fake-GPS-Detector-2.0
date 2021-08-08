package com.dariusz.fakegpsdetector.data.local.sensor.wifinodes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface WifiScanResultsData {

    suspend fun getCurrentScanResultsLive(): Flow<List<ScanResult>>

}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class WifiScanResultsDataImpl
@Inject
constructor(
    private val context: Context
) : WifiScanResultsData {

    override suspend fun getCurrentScanResultsLive(): Flow<List<ScanResult>> =
        context.getCurrentScanResultsAsFlow()

    private suspend fun Context.getCurrentScanResultsAsFlow(): Flow<List<ScanResult>> {
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        return callbackFlow {
            val wifiScanReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    if (intent.action == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                        wifiManager.scanResults.let { trySend(it).isSuccess }
                    }
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
