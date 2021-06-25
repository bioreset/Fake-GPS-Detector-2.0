package com.dariusz.fakegpsdetector.utils

import android.Manifest
import android.content.Context
import com.dariusz.fakegpsdetector.R
import kotlinx.coroutines.Dispatchers

object Constants {

    // API SETUP
    const val API_URL = "https://www.googleapis.com/"
    const val API_HEADER = "Content-Type: application/json"
    const val API_POST = "geolocation/v1/geolocate"
    fun getApiKey(context: Context) = context.getString(R.string.google_maps_api_key)

    // PERMISSION SETUP
    val permissionToWatch = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.READ_PHONE_STATE
    )

    // MAP SETUP
    const val InitialZoom = 20F
    const val MinZoom = 2F
    const val MaxZoom = 60F

    // DISPATCHERS
    val dispatcherForSafeCalls = Dispatchers.IO

}
