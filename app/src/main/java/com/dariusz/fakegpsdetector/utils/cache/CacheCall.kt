package com.dariusz.fakegpsdetector.utils.cache

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

object CacheCall {

    suspend fun <T> safeCacheCall(
        dispatcher: CoroutineDispatcher,
        cacheCall: suspend () -> T?
    ): CacheStatus<T?> {
        return withContext(dispatcher) {
            try {
                withTimeout(2000) {
                    CacheStatus.Success(cacheCall.invoke())
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is TimeoutCancellationException -> {
                        CacheStatus.CacheError(
                            handleError("cache-error", "TimeoutCancellationException Exception")
                        )
                    }
                    else -> {
                        CacheStatus.CacheError(
                            handleError(
                                "cache-error",
                                "Unknown Error"
                            )
                        )
                    }
                }
            }
        }
    }
}
