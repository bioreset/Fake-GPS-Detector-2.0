package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.remote.api.FakeGPSRestApiService
import com.dariusz.fakegpsdetector.domain.model.ApiRequestModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationFromApiResponseRepository
@Inject
constructor(
    private val fakeGPSRestApiService: FakeGPSRestApiService
) {

    suspend fun checkCurrentLocationOfTheDevice(
        body: ApiRequestModel
    ) = flow { emit(fakeGPSRestApiService.checkCurrentLocation(body)) }

}
