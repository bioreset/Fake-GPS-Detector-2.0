package com.dariusz.fakegpsdetector.data.source.local.sensor.requirements

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.lifecycle.LiveData
import com.dariusz.fakegpsdetector.model.WifiStatusModel

class WifiStatusLiveData(private val context: Context) : LiveData<WifiStatusModel>() {

    private val wifiStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = isWifiEnabled()
    }

    private var wifiManager: WifiManager =
        context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun onActive() {
        registerReceiver()
        isWifiEnabled()
    }

    override fun onInactive() =
        unregisterReceiver()

    private fun isWifiEnabled() {
        if (wifiManager.isWifiEnabled)
            postValue(WifiStatusModel(status = true))
        else
            postValue(WifiStatusModel(status = false))
    }

    private fun registerReceiver() {
        context.registerReceiver(
            wifiStatusReceiver,
            IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        )
    }

    private fun unregisterReceiver() {
        context.unregisterReceiver(wifiStatusReceiver)
    }
}
