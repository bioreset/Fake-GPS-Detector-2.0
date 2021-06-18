package com.dariusz.fakegpsdetector.utils.cache

import com.dariusz.fakegpsdetector.model.ErrorContent

sealed class CacheStatus<out T> {

    data class Success<out T>(val value: T) : CacheStatus<T>()

    data class CacheError(
        val errorHandle: ErrorContent
    ) : CacheStatus<Nothing>()
}
