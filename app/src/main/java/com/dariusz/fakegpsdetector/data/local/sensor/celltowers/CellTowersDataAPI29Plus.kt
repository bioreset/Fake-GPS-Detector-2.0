package com.dariusz.fakegpsdetector.data.local.sensor.celltowers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.CellInfo
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@Singleton
class CellTowersDataAPI29Plus
@Inject
constructor(@ApplicationContext private val context: Context) {

    fun getCurrentCellTowersLive(): Flow<List<CellInfo>> =
        context.getCurrentCellTowersAsFlow()

    private fun Context.getCurrentCellTowersAsFlow(): Flow<List<CellInfo>> {
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

            }
            awaitClose {
                cancel()
            }
        }
    }

}