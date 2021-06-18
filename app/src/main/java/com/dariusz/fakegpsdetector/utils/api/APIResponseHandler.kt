package com.dariusz.fakegpsdetector.utils.api

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError

object APIResponseHandler {

    @Suppress("UNCHECKED_CAST")
    fun <T> getResultFromAPI(response: APIStatus<T>): T {
        return when (response) {
            is APIStatus.APIError -> {
                handleError(
                    "generic",
                    "Unknown Error"
                ) as T
            }
            is APIStatus.NetworkError -> {
                handleError(
                    "network",
                    "Network Error"
                ) as T
            }
            is APIStatus.Success -> {
                response.value ?: handleError("api-error", "Data is empty") as T
            }
        }
    }
}
