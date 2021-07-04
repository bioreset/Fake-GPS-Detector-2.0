package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.domain.model.ApiRequestModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import com.dariusz.fakegpsdetector.utils.RepositoryUtils.performApiCall
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
class LocationFromApiResponseRepository
@Inject
constructor(
    private val fakeGPSRestApiService: FakeGPSRestApiService
) {
    suspend fun checkCurrentLocationOfTheDevice(
        body: ApiRequestModel
    ): ApiResponseModel =
        performApiCall(
            "check-current-location-api-call",
            fakeGPSRestApiService.checkCurrentLocation(body)
        )
}
