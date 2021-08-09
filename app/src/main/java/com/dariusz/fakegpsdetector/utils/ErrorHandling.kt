package com.dariusz.fakegpsdetector.utils

import android.util.Log
import com.dariusz.fakegpsdetector.domain.model.ErrorContent
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException

object ErrorHandling {

    fun Throwable.displayError() = when (this) {
        is TimeoutCancellationException -> {
            handleError(
                "api-network-error: ",
                "TimeoutCancellationException Exception"
            )
        }
        is IOException -> {
            handleError("api-network-error: ", "I/O Exception")
        }
        is HttpException -> {
            val code = code()
            val errorResponse = convertErrorBody(this)
            handleError(
                "api-http-error: ",
                "Error code: $code; Error response: $errorResponse"
            )

        }
        is AccessDeniedException -> {
            handleError(
                "sensor-error:",
                "AccessDeniedException Exception"
            )
        }
        else -> {
            handleError(
                "sensor-error: ",
                "Unknown Error"
            )
        }
    }.also {
        printStackTrace()
    }


    private fun handleError(errorType: String, errorMessage: String) {
        errorAsLog(ErrorContent(errorType, errorMessage))
    }

    private fun errorAsLog(errorHandle: ErrorContent): Int {
        return Log.e(
            "Error handling system detected some problems: ",
            "Error type: ${errorHandle.errorType}, Error message: ${errorHandle.errorMessage}"
        )
    }

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()?.errorBody()?.string()
        } catch (exception: Exception) {
            "Unknown error"
        }
    }
}
