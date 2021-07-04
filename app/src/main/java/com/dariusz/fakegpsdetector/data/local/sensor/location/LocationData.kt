package com.dariusz.fakegpsdetector.data.local.sensor.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performSensorCall
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

interface LocationData {

    suspend fun getCurrentLocationLive(): Flow<LocationModel>

    suspend fun getCurrentLocationOnce(): LocationModel


}

@SuppressLint("MissingPermission")
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class LocationDataImpl
@Inject
constructor(
    private val context: Context
) : LocationData {

    override suspend fun getCurrentLocationLive(): Flow<LocationModel> {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        return performSensorCall(
            "get-current-location-live",
            fusedLocationProviderClient.getCurrentLocationAsFlow()
        )
    }

    override suspend fun getCurrentLocationOnce(): LocationModel {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        return performSensorCall(
            "get-current-location-once",
            fusedLocationProviderClient.getCurrentLocationOnce()
        )
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
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    for (location in locationResult.locations) {
                        this@callbackFlow.trySend(setLocationData(location)).isSuccess
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

    private suspend fun FusedLocationProviderClient.getCurrentLocationOnce(): LocationModel =
        suspendCancellableCoroutine { continuation ->
            val locationRequest: LocationRequest = LocationRequest.create()
                .apply {
                    interval = 1000L
                    fastestInterval = 500L
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    for (location in locationResult.locations) {
                        removeLocationUpdates(this)
                        continuation.resume(setLocationData(location))
                    }
                }
            }
            requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            continuation.invokeOnCancellation {
                removeLocationUpdates(locationCallback)
            }
        }

    private fun setLocationData(location: Location) =
        LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
}
