package com.dariusz.fakegpsdetector.data.remote.api

import android.content.Context
import com.dariusz.fakegpsdetector.domain.model.ApiRequestModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel

interface FakeGPSRestApiService {

    suspend fun checkCurrentLocation(
        jsonBody: ApiRequestModel,
        context: Context
    ): ApiResponseModel
}
