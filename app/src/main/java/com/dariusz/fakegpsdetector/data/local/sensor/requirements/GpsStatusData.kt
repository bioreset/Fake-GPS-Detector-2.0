package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.location.LocationManager
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GpsStatusData {

    val currentGpsStatus: Flow<GpsStatusModel>

}

class GpsStatusDataImpl
@Inject
constructor(
    @ApplicationContext private val context: Context
) : GpsStatusData {

    override val currentGpsStatus: Flow<GpsStatusModel> = context.liveGpsStatus()

    private fun Context.liveGpsStatus(): Flow<GpsStatusModel> {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return flow { emit(checkGpsStatus(locationManager)) }
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
