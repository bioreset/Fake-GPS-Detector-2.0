package com.dariusz.fakegpsdetector.data.repository

import android.content.Context
import com.dariusz.fakegpsdetector.data.source.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.model.ApiRequestModel
import com.dariusz.fakegpsdetector.model.ApiResponseModel
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performApiCall
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class LocationFromApiResponseRepository
@Inject
constructor(
    private val fakeGPSRestApiService: FakeGPSRestApiService
) {
    suspend fun checkCurrentLocationOfTheDevice(body: ApiRequestModel, context: Context): ApiResponseModel =
        performApiCall(
            "check-current-location-api-call",
            fakeGPSRestApiService.checkCurrentLocation(body, context)
        )
}
