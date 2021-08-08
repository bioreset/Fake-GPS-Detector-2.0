package com.dariusz.fakegpsdetector.domain.model

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val throwable: Throwable) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
    object Idle : ResultState<Nothing>()
}