package com.dariusz.fakegpsdetector.utils

import android.location.Location
import com.dariusz.fakegpsdetector.domain.model.LocationData
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import kotlin.math.roundToInt

class DistanceCalculator(
    private val locationOne: LocationModel,
    private val locationTwo: LocationData
) {

    private var locationNumberOne: Location = Location("one")
    private var locationNumberTwo: Location = Location("two")

    private fun mapLocations() {
        locationNumberOne.latitude = locationOne.latitude
        locationNumberOne.longitude = locationOne.longitude
        locationNumberTwo.latitude = locationTwo.lat ?: 0.0
        locationNumberTwo.longitude = locationTwo.lng ?: 0.0
    }

    private fun calculateDistance() = locationNumberOne.distanceTo(locationNumberTwo).roundToInt()

    fun isRealLocation(accuracy: Int): Boolean {
        mapLocations()
        return when {
            calculateDistance() < 2 * accuracy -> {
                true
            }
            calculateDistance() == accuracy -> {
                true
            }
            else -> {
                false
            }
        }
    }
}