package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.domain.model.ApiRequestModel
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationFromApiResponseRepository
@Inject
constructor(
    private val fakeGPSRestApiService: FakeGPSRestApiService
) {

    suspend fun checkCurrentLocationOfTheDevice(
        body: ApiRequestModel
    ) = flow { emit(fakeGPSRestApiService.checkCurrentLocation(body)) }

}
