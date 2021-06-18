package com.dariusz.fakegpsdetector.utils

import com.dariusz.fakegpsdetector.utils.api.APICall.safeApiCall
import com.dariusz.fakegpsdetector.utils.api.APIResponseHandler.getResultFromAPI
import com.dariusz.fakegpsdetector.utils.cache.CacheCall.safeCacheCall
import com.dariusz.fakegpsdetector.utils.cache.CacheResponseHandler.getResultFromCache
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
object RepositoryUtils {

    suspend fun <T> performApiCall(call: T): T? {
        val safeCall = safeApiCall(IO) { call }
        return getResultFromAPI(safeCall)
    }

    suspend fun <T> performCacheCall(call: T): T? {
        val safeCall = safeCacheCall(IO) { call }
        return getResultFromCache(safeCall)
    }
}
