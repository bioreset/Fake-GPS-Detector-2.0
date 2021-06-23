package com.dariusz.fakegpsdetector.data.source.local.sensor.celltowers

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
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class CellTowersData
@Inject
constructor(private val context: Context) {

    suspend fun getCurrentCellTowersOnce() =
        performSensorCall("get-current-cell-towers-once", context.getCurrentCellTowers())

    suspend fun getCurrentCellTowersLive() =
        performSensorCall("get-current-cell-towers-live", context.getCurrentCellTowersAsFlow())

    private suspend fun Context.getCurrentCellTowers(): List<CellInfo> {
        val telephonyManager: TelephonyManager =
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return suspendCancellableCoroutine { continuation ->
            val wifiScanReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                        unregisterReceiver(this)
                        continuation.resume(telephonyManager.allCellInfo)
                    }
                }
            }
            registerReceiver(
                wifiScanReceiver,
                IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            )
            continuation.invokeOnCancellation {
                unregisterReceiver(wifiScanReceiver)
            }
        }
    }

    private suspend fun Context.getCurrentCellTowersAsFlow(): Flow<List<CellInfo>> {
        val telephonyManager: TelephonyManager =
            getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return callbackFlow {
            val wifiScanReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
                        telephonyManager.allCellInfo.let { trySend(it).isSuccess }
                    }
                }
            }
            registerReceiver(
                wifiScanReceiver,
                IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
            )
            awaitClose {
                unregisterReceiver(wifiScanReceiver)
            }
        }.shareIn(
            MainScope(),
            SharingStarted.WhileSubscribed()
        )
    }
}
