package com.dariusz.fakegpsdetector.utils.sensor

import com.dariusz.fakegpsdetector.model.ErrorContent

sealed class SensorStatus<out T> {

    data class Success<out T>(val value: T) : SensorStatus<T>()

    data class SensorError(
        val errorHandle: ErrorContent
    ) : SensorStatus<Nothing>()
}
