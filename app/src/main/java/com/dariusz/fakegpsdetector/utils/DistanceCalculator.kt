package com.dariusz.fakegpsdetector.utils

import android.location.Location
import kotlin.math.roundToInt

object DistanceCalculator {

    private var location1: Location = Location("one")
    private var location2: Location = Location("two")

    private var result: Boolean = false

    fun calculateDistance(
        location1lat: Double,
        location1lng: Double,
        location2lat: Double,
        location2lng: Double
    ): Int {
        location1.latitude = location1lat
        location1.longitude = location1lng

        location2.latitude = location2lat
        location2.longitude = location2lng

        val distance = location1.distanceTo(location2)
        return distance.roundToInt()
    }

    fun isRealLocation(distance: Int, accuracy: Int): Boolean {
        result = when {
            distance < 2 * accuracy -> {
                true
            }
            distance == accuracy -> {
                true
            }
            else -> {
                false
            }
        }
        return result
    }
}
