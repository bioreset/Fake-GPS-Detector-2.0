package com.dariusz.fakegpsdetector.utils

import android.util.Log
import com.dariusz.fakegpsdetector.model.ErrorContent

object ErrorHandling {

    fun handleError(errorType: String, errorMessage: String): ErrorContent {
        val result = ErrorContent(errorType, errorMessage)
        return result.also { errorAsLog(it) }
    }

    private fun errorAsLog(errorHandle: ErrorContent): Int {
        return Log.e(
            "Error handling system detected some problems: ",
            "Error type: ${errorHandle.errorType}, Error message: ${errorHandle.errorMessage}"
        )
    }
}
