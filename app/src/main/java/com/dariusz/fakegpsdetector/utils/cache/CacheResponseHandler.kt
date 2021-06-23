package com.dariusz.fakegpsdetector.utils.cache

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError

object CacheResponseHandler {

    @Suppress("UNCHECKED_CAST")
    fun <T> getResultFromCache(operation: String, response: CacheStatus<T>): T {
        return when (response) {
            is CacheStatus.CacheError -> {
                handleError("cache-error: $operation", "Unknown Cache Error") as T
            }
            is CacheStatus.Success -> {
                response.value ?: handleError("cache-error: $operation", "Data is empty") as T
            }
        }
    }
}
