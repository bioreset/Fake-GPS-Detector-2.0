package com.dariusz.fakegpsdetector.utils

import android.location.Location
import com.dariusz.fakegpsdetector.model.LocationData
import com.dariusz.fakegpsdetector.model.LocationModel
import kotlin.math.roundToInt

class DistanceCalculator(
    private val locationOne: LocationModel,
    private val locationTwo: LocationData
) {

    private var locationNumberOne: Location = Location("one")
    private var locationNumberTwo: Location = Location("two")

    private fun mapLocations() {
        locationNumberOne.latitude = locationOne.latitude ?: 0.0
        locationNumberOne.longitude = locationOne.longitude ?: 0.0
        locationNumberTwo.latitude = locationTwo.lat ?: 0.0
        locationNumberTwo.longitude = locationTwo.lng ?: 0.0
    }

    fun calculateDistance() = locationNumberOne.distanceTo(locationNumberTwo).roundToInt()

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