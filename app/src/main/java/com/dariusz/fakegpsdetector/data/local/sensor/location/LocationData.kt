package com.dariusz.fakegpsdetector.data.local.sensor.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.properties.Delegates

interface LocationData {

    suspend fun getCurrentLocation(): Flow<LocationModel>

}

@SuppressLint("MissingPermission")
class LocationDataImpl
@Inject
constructor(
    @ApplicationContext private val context: Context
) : LocationData {

    override suspend fun getCurrentLocation(): Flow<LocationModel> {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        return fusedLocationProviderClient.getCurrentLocationAsFlow()

    }

    private suspend fun FusedLocationProviderClient.getCurrentLocationAsFlow(): Flow<LocationModel> =
        callbackFlow {
            val locationRequest: LocationRequest = LocationRequest.create()
                .apply {
                    interval = 1000L
                    fastestInterval = 500L
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    for (location in p0.locations) {
                        trySend(setLocationData(location))
                    }
                }
            }
            requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            awaitClose {
                removeLocationUpdates(locationCallback)
            }

        }

    private fun setLocationData(location: Location) =
        LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
}
