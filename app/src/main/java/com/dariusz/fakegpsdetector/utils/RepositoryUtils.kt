package com.dariusz.fakegpsdetector.utils

import com.dariusz.fakegpsdetector.utils.Constants.dispatcherForSafeCalls
import com.dariusz.fakegpsdetector.utils.api.APICall.safeApiCall
import com.dariusz.fakegpsdetector.utils.api.APIResponseHandler.getResultFromAPI
import com.dariusz.fakegpsdetector.utils.sensor.SensorCall.safeSensorCall
import com.dariusz.fakegpsdetector.utils.sensor.SensorResponseHandler.getResultFromSensor
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object RepositoryUtils {

    suspend fun <T> performApiCall(operation: String, call: T): T {
        val safeCall = safeApiCall(dispatcherForSafeCalls, operation) { call }
        return getResultFromAPI(operation, safeCall)
    }

    suspend fun <T> performSensorCall(operation: String, call: T): T {
        val safeCall = safeSensorCall(dispatcherForSafeCalls, operation) { call }
        return getResultFromSensor(operation, safeCall)
    }
}
