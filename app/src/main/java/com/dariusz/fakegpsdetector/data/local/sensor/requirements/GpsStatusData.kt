package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.location.LocationManager
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

interface GpsStatusData {

    val currentGpsStatus: GpsStatusModel

}

@ExperimentalCoroutinesApi
class GpsStatusDataImpl
@Inject
constructor(
    context: Context
) : GpsStatusData {

    override val currentGpsStatus: GpsStatusModel = context.liveGpsStatus()

    private fun Context.liveGpsStatus(): GpsStatusModel {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return checkGpsStatus(locationManager)
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
