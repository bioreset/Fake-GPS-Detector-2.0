package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.domain.model.ApiRequestModel
import com.dariusz.fakegpsdetector.domain.model.ApiResponseModel
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

interface LocationFromApiResponseRepository {

    suspend fun checkCurrentLocationOfTheDevice(
        body: ApiRequestModel
    ): ApiResponseModel

}

@InternalCoroutinesApi
class LocationFromApiResponseRepositoryImpl
@Inject
constructor(
    private val fakeGPSRestApiService: FakeGPSRestApiService
) : LocationFromApiResponseRepository {

    override suspend fun checkCurrentLocationOfTheDevice(
        body: ApiRequestModel
    ) = fakeGPSRestApiService.checkCurrentLocation(body)

}
