package com.dariusz.fakegpsdetector.data.local.sensor.celltowers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface CellTowersData {

    suspend fun getCurrentCellTowersLive(): Flow<List<CellInfo>>

}

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CellTowersDataImpl
@Inject
constructor(private val context: Context) : CellTowersData {

    override suspend fun getCurrentCellTowersLive(): Flow<List<CellInfo>> =
        context.getCurrentCellTowersAsFlow()

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
        }

    }
}
