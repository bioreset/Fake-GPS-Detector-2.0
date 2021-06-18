package com.dariusz.fakegpsdetector.data.source.local.sensor.requirements

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData
import com.dariusz.fakegpsdetector.model.GpsStatusModel

class GpsStatusLiveData(private var context: Context) : LiveData<GpsStatusModel>() {

    private val gpsSwitchStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = checkGpsAndReact()
    }

    override fun onInactive() = unregisterReceiver()

    override fun onActive() {
        registerReceiver()
        checkGpsAndReact()
    }

    private fun checkGpsAndReact() =
        if (isLocationEnabled()) {
            postValue(GpsStatusModel(status = true))
        } else {
            postValue(GpsStatusModel(status = false))
        }

    private fun isLocationEnabled() =
        (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )

    private fun registerReceiver() =
        context.registerReceiver(
            gpsSwitchStateReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )

    private fun unregisterReceiver() = context.unregisterReceiver(gpsSwitchStateReceiver)
}
