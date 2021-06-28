package com.dariusz.fakegpsdetector.data.local.sensor.celltowers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performSensorCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CellTowersData
@Inject
constructor(private val context: Context) {

    suspend fun getCurrentCellTowersLive() =
        performSensorCall("get-current-cell-towers-live", context.getCurrentCellTowersAsFlow())

    private suspend fun Context.getCurrentCellTowersAsFlow(): Flow<List<CellInfo>> {
        val telephonyManager: TelephonyManager =
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return callbackFlow {
            val cellTowersScanReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                        telephonyManager.allCellInfo.let { trySend(it).isSuccess }
                    }
                }
            }
            registerReceiver(
                cellTowersScanReceiver,
                IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            )
            awaitClose {
                unregisterReceiver(cellTowersScanReceiver)
            }
        }.shareIn(
            MainScope(),
            SharingStarted.WhileSubscribed()
        )
    }
}
