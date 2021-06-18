package com.dariusz.fakegpsdetector.data.source.local.sensor.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.dariusz.fakegpsdetector.model.LocationModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient

class LocationLiveData(context: Context) : LiveData<LocationModel>() {

    private val fusedLocationProviderClient =
        getFusedLocationProviderClient(context)

    private val locationRequest: LocationRequest = LocationRequest.create()
        .apply {
            interval = 1000L
            fastestInterval = 500L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.also {
                    setLocationData(it)
                }
            }
        startLocationUpdates()
    }

    override fun onInactive() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun setLocationData(location: Location) {
        postValue(
            LocationModel(
                longitude = location.longitude,
                latitude = location.latitude
            )
        )
    }
}
