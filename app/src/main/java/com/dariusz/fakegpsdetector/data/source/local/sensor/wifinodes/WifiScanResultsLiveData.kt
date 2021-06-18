package com.dariusz.fakegpsdetector.data.source.local.sensor.wifinodes

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.lifecycle.LiveData

class WifiScanResultsLiveData(private var context: Context) : LiveData<List<ScanResult>>() {

    private val wifiScanResultsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                performScanning()
            }
        }
    }

    private var wifiManager: WifiManager =
        context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    private val scanResults = wifiManager.scanResults

    override fun onInactive() = unregisterReceiver()

    override fun onActive() {
        registerReceiver()
        performScanning()
    }

    private fun performScanning() {
        postValue(scanResults)
    }

    private fun registerReceiver() {
        context.registerReceiver(
            wifiScanResultsReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
    }

    private fun unregisterReceiver() {
        context.unregisterReceiver(wifiScanResultsReceiver)
    }
}
