package com.dariusz.fakegpsdetector.utils.sensor

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

object SensorCall {

    suspend fun <T> safeSensorCall(
        dispatcher: CoroutineDispatcher,
        operation: String,
        sensorCall: suspend () -> T
    ): SensorStatus<T> {
        return withContext(dispatcher) {
            try {
                withTimeout(2000) {
                    SensorStatus.Success(sensorCall.invoke())
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is AccessDeniedException -> {
                        SensorStatus.SensorError(
                            handleError(
                                "sensor-error: $operation",
                                "AccessDeniedException Exception"
                            )
                        )
                    }
                    else -> {
                        SensorStatus.SensorError(
                            handleError(
                                "sensor-error: $operation",
                                "Unknown Error: $throwable"
                            )
                        )
                    }
                }
            }
        }
    }

}