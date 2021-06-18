package com.dariusz.fakegpsdetector.data.source.local.sensor.celltowers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import androidx.lifecycle.LiveData

class CellTowersLiveData(private var context: Context) : LiveData<List<CellInfo>>() {

    private val cellTowersScanResultsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = onCellInfoChange()
    }

    private val telephonyManager: TelephonyManager
        get() = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    @SuppressLint("MissingPermission")
    private var allCellInfo = telephonyManager.allCellInfo

    override fun onActive() {
        registerReceiver()
        postValue(allCellInfo)
    }

    override fun onInactive() =
        unregisterReceiver()

    private fun onCellInfoChange() {
        allCellInfo
    }

    private fun registerReceiver() {
        context.registerReceiver(
            cellTowersScanResultsReceiver,
            IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        )
    }

    private fun unregisterReceiver() {
        context.unregisterReceiver(cellTowersScanResultsReceiver)
    }
}
