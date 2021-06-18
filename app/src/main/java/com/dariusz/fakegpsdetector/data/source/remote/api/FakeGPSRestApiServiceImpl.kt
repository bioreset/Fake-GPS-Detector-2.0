package com.dariusz.fakegpsdetector.data.source.remote.api

import android.content.Context
import com.dariusz.fakegpsdetector.di.NetworkModule.provideRetrofit
import com.dariusz.fakegpsdetector.utils.Constants.getApiKey
import kotlinx.coroutines.InternalCoroutinesApi

class FakeGPSRestApiServiceImpl : FakeGPSRestApiService {

    @InternalCoroutinesApi
    override suspend fun checkCurrentLocation(
        jsonBody: String,
        context: Context
    ): String =
        provideRetrofit().checkLocation(
            jsonBody,
            getApiKey(context)
        )
}
