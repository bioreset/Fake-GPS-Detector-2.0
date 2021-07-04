package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface GpsStatusData {

    val currentGpsStatus: Flow<GpsStatusModel>

}

@ExperimentalCoroutinesApi
class GpsStatusDataImpl
@Inject
constructor(
    context: Context
) : GpsStatusData {

    override val currentGpsStatus: Flow<GpsStatusModel> = context.liveGpsStatus()

    private fun Context.liveGpsStatus(): Flow<GpsStatusModel> {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsStatus: GpsStatusModel = checkGpsStatus(locationManager)
        return callbackFlow {
            val gpsSwitchStateReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context, intent: Intent) {
                    if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                        unregisterReceiver(this)
                        this@callbackFlow.trySend(gpsStatus).isSuccess
                    }
                }
            }
            registerReceiver(
                gpsSwitchStateReceiver,
                IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            )
            awaitClose {
                unregisterReceiver(gpsSwitchStateReceiver)
            }
        }
    }

    private fun checkGpsStatus(locationManager: LocationManager) = isGpsEnabled(
        locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )
    )

    private fun isGpsEnabled(status: Boolean): GpsStatusModel {
        return if (status)
            GpsStatusModel(status = true)
        else
            GpsStatusModel(status = false)
    }
}
