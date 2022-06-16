package com.dariusz.fakegpsdetector.domain.repository

import com.dariusz.fakegpsdetector.data.local.sensor.location.LocationData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository
@Inject constructor(
    private val locationData: LocationData
) {

    fun getLocationData() = locationData.getCurrentLocation()

}