package com.dariusz.fakegpsdetector.utils.api

import com.dariusz.fakegpsdetector.model.ErrorContent

sealed class APIStatus<out T> {

    data class Success<out T>(val value: T) : APIStatus<T>()

    data class APIError(
        val errorHandle: ErrorContent
    ) : APIStatus<Nothing>()

    data class NetworkError(
        val errorHandle: ErrorContent
    ) : APIStatus<Nothing>()
}
