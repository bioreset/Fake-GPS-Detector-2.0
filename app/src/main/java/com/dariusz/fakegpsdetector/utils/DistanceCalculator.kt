package com.dariusz.fakegpsdetector.utils

import android.location.Location
import com.dariusz.fakegpsdetector.domain.model.LocationData
import com.dariusz.fakegpsdetector.domain.model.LocationModel
import kotlin.math.roundToInt

object DistanceCalculator {

    fun getFinalResult(
       locationOne: LocationModel?,
       locationTwo: LocationData?,
       accuracy: Double?
    ): FinalResult {

        val locationNumberOne = Location("local")
        val locationNumberTwo = Location("remote")

        locationNumberOne.latitude = locationOne?.latitude ?: 0.0
        locationNumberOne.longitude = locationOne?.longitude ?: 0.0
        locationNumberTwo.latitude = locationTwo?.lat ?: 0.0
        locationNumberTwo.longitude = locationTwo?.lng ?: 0.0

        val distance = locationNumberOne.distanceTo(locationNumberTwo).roundToInt()

        val isSpoofed = when {
            distance < 2 * (accuracy?.toInt() ?: 0) -> {
                true
            }
            distance == (accuracy?.toInt() ?: 0) -> {
                true
            }
            else -> {
                false
            }
        }

        return FinalResult(distance, isSpoofed)
    }

    data class FinalResult(
        val distance: Int,
        val isSpoofed: Boolean
    )

}

