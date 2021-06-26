package com.dariusz.fakegpsdetector.data.source.remote.api

import android.content.Context
import com.dariusz.fakegpsdetector.model.ApiRequestModel
import com.dariusz.fakegpsdetector.model.ApiResponseModel

interface FakeGPSRestApiService {

    suspend fun checkCurrentLocation(
        jsonBody: ApiRequestModel,
        context: Context
    ): ApiResponseModel
}
