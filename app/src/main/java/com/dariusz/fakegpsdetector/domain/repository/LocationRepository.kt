package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.location.LocationData
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocationRepository {

    suspend fun getLocationDataOnce(): LocationModel

    suspend fun getLocationDataLive(): Flow<LocationModel>

}

class LocationRepositoryImpl
@Inject constructor(
    private val locationData: LocationData
) : LocationRepository {

    override suspend fun getLocationDataOnce() = locationData.getCurrentLocationOnce()

    override suspend fun getLocationDataLive() = locationData.getCurrentLocationLive()

}