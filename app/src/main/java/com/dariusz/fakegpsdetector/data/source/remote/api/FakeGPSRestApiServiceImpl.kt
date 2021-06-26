package com.dariusz.fakegpsdetector.data.source.remote.api

import android.content.Context
import com.dariusz.fakegpsdetector.di.NetworkModule.provideRetrofit
import com.dariusz.fakegpsdetector.model.ApiRequestModel
import com.dariusz.fakegpsdetector.model.ApiResponseModel
import com.dariusz.fakegpsdetector.utils.Constants.getApiKey
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.InternalCoroutinesApi

class FakeGPSRestApiServiceImpl : FakeGPSRestApiService {

    @InternalCoroutinesApi
    override suspend fun checkCurrentLocation(
        jsonBody: ApiRequestModel,
        context: Context
    ): ApiResponseModel =
        provideRetrofit().checkLocation(
            toJsonString(jsonBody),
            getApiKey(context)
        )

    private val moshi: Moshi = Moshi.Builder().build()

    private fun toJsonString(input: ApiRequestModel) : String {
        val adapter: JsonAdapter<ApiRequestModel> = moshi.adapter(ApiRequestModel::class.java)
        return adapter.toJson(input)
    }
}
