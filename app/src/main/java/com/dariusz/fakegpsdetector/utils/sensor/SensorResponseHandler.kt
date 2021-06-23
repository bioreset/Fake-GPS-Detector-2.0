package com.dariusz.fakegpsdetector.utils.sensor

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError

object SensorResponseHandler {

    @Suppress("UNCHECKED_CAST")
    fun <T> getResultFromSensor(operation: String, response: SensorStatus<T>): T {
        return when (response) {
            is SensorStatus.SensorError -> {
                handleError("sensor-error: $operation", "Unknown Sensor Error") as T
            }
            is SensorStatus.Success -> {
                response.value ?: handleError("sensor-error: $operation", "Data is empty") as T
            }
        }
    }

}