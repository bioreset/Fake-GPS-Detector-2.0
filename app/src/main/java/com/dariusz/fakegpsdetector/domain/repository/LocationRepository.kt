package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.location.LocationData
import javax.inject.Inject

class LocationRepository
@Inject constructor(
    private val locationData: LocationData
) {

    suspend fun getLocationData() = locationData.getCurrentLocation()

}