package com.dariusz.fakegpsdetector.data.local.sensor.celltowers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.CellInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface CellTowersDataAPI29Plus {

    suspend fun getCurrentCellTowersLive(): Flow<List<CellInfo>>

}

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.Q)
class CellTowersDataAPI29PlusImpl
@Inject
constructor(private val context: Context) : CellTowersDataAPI29Plus {

    override suspend fun getCurrentCellTowersLive(): Flow<List<CellInfo>> =
        context.getCurrentCellTowersAsFlow()

    private suspend fun Context.getCurrentCellTowersAsFlow(): Flow<List<CellInfo>> {
        val subscriptionManager = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE)
                as SubscriptionManager
        return callbackFlow {
            for (subscriptions in subscriptionManager.activeSubscriptionInfoList) {
                val telephonyManager: TelephonyManager =
                    (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)
                        .createForSubscriptionId(subscriptions.subscriptionId)
                val cellTowersScanReceiver = object : TelephonyManager.CellInfoCallback() {
                    override fun onCellInfo(cellInfo: MutableList<CellInfo>) {
                        cellInfo.let { trySend(it).isSuccess }
                    }
                }
                telephonyManager.requestCellInfoUpdate(mainExecutor, cellTowersScanReceiver)
                awaitClose {
                    cancel()
                }
            }

        }
    }

}