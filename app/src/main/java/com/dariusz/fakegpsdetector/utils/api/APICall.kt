package com.dariusz.fakegpsdetector.utils.api

import com.dariusz.fakegpsdetector.utils.ErrorHandling.handleError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okio.IOException
import retrofit2.HttpException

object APICall {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        operation: String,
        apiCall: suspend () -> T
    ): APIStatus<T> {
        return withContext(dispatcher) {
            try {
                withTimeout(6000) {
                    APIStatus.Success(apiCall.invoke())
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                when (throwable) {
                    is TimeoutCancellationException -> {
                        APIStatus.NetworkError(
                            handleError(
                                "api-network-error: $operation",
                                "TimeoutCancellationException Exception"
                            )
                        )
                    }
                    is IOException -> {
                        APIStatus.NetworkError(
                            handleError("api-network-error: $operation", "I/O Exception")
                        )
                    }
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse =
                            convertErrorBody(
                                throwable
                            )
                        APIStatus.APIError(
                            handleError(
                                "api-http-error: $operation",
                                "Error code: $code; Error response: $errorResponse"
                            )
                        )
                    }
                    else -> {
                        APIStatus.APIError(
                            handleError("api-error: $operation", "Unknown Error: $throwable")
                        )
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()?.errorBody()?.string()
        } catch (exception: Exception) {
            "Unknown error"
        }
    }
}
