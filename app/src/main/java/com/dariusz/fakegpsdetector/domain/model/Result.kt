package com.dariusz.fakegpsdetector.domain.model

sealed class Result<out T> {
    data class Success<T>(var data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
