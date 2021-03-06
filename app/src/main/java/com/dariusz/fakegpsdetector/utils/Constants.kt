package com.dariusz.fakegpsdetector.utils

import android.Manifest

object Constants {

    // API SETUP
    const val API_URL = "https://www.googleapis.com/"
    const val API_HEADER = "Content-Type: application/json"
    const val API_POST = "geolocation/v1/geolocate"
    const val API_KEY = "YOUR_GOOGLE_MAPS_API_KEY"

    // PERMISSION SETUP
    val permissionsToWatch = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE,
        Manifest.permission.READ_PHONE_STATE
    )

    // MAP SETUP
    const val InitialZoom = 20F

}
