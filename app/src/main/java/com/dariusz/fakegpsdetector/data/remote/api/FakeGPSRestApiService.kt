package com.dariusz.fakegpsdetector.data.remote.api

import com.dariusz.fakegpsdetector.di.NetworkModule.provideRetrofit
import com.dariusz.fakegpsdetector.domain.model.ApiRequestModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import com.dariusz.fakegpsdetector.utils.Constants.API_KEY
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.InternalCoroutinesApi

interface FakeGPSRestApiService {

    suspend fun checkCurrentLocation(
        jsonBody: ApiRequestModel
    ): ApiResponseModel
}

class FakeGPSRestApiServiceImpl : FakeGPSRestApiService {

    @InternalCoroutinesApi
    override suspend fun checkCurrentLocation(
        jsonBody: ApiRequestModel
    ): ApiResponseModel =
        provideRetrofit().checkLocation(
            toJsonString(jsonBody),
            API_KEY
        )

    private fun toJsonString(input: ApiRequestModel): String {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<ApiRequestModel> = moshi.adapter(ApiRequestModel::class.java)
        return adapter.toJson(input)
    }
}

