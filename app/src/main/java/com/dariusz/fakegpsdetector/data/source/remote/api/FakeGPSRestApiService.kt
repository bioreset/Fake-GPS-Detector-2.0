package com.dariusz.fakegpsdetector.data.source.remote.api

import android.content.Context

interface FakeGPSRestApiService {

    suspend fun checkCurrentLocation(
        jsonBody: String,
        context: Context
    ): String
}
