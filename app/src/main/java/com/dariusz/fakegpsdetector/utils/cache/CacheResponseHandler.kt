package com.dariusz.fakegpsdetector.utils.cache

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError

object CacheResponseHandler {

    @Suppress("UNCHECKED_CAST")
    fun <T> getResultFromCache(response: CacheStatus<T>): T {
        return when (response) {
            is CacheStatus.CacheError -> {
                handleError("cache-error", "Unknown Cache Error") as T
            }
            is CacheStatus.Success -> {
                response.value ?: handleError("cache-error", "Data is empty") as T
            }
        }
    }
}
