package com.dariusz.fakegpsdetector.utils

import android.content.Context
import com.dariusz.fakegpsdetector.R

object Constants {

    // API SETUP
    const val API_URL = "https://www.googleapis.com/"
    const val API_HEADER = "Content-Type: application/json"
    const val API_POST = "geolocation/v1/geolocate"

    // ROOM SETUP
    const val DB_NAME = "fgd_database_final"
    const val DB_VER = 1

    fun getApiKey(context: Context) = context.getString(R.string.google_maps_api_key)
}
