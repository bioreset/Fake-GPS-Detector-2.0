package com.dariusz.fakegpsdetector.data.remote.api

import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import com.dariusz.fakegpsdetector.utils.Constants.API_HEADER
import com.dariusz.fakegpsdetector.utils.Constants.API_POST
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface FakeGPSRestApi {

    @Headers(API_HEADER)
    @POST(API_POST)
    suspend fun checkLocation(
        @Body body: String,
        @Query("key") key: String
    ): ApiResponseModel
}
