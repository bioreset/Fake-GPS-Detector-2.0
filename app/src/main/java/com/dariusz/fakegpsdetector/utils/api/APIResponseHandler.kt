package com.dariusz.fakegpsdetector.utils.api

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError

object APIResponseHandler {

    @Suppress("UNCHECKED_CAST")
    fun <T> getResultFromAPI(operation: String, response: APIStatus<T>): T {
        return when (response) {
            is APIStatus.APIError -> {
                handleError(
                    "generic: $operation",
                    "Unknown Error"
                ) as T
            }
            is APIStatus.NetworkError -> {
                handleError(
                    "network: $operation",
                    "Network Error"
                ) as T
            }
            is APIStatus.Success -> {
                response.value ?: handleError("api-error: $operation", "Data is empty") as T
            }
        }
    }
}
