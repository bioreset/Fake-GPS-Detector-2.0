package com.dariusz.fakegpsdetector.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.dariusz.fakegpsdetector.domain.model.Result
import com.dariusz.fakegpsdetector.presentation.components.common.CenteredText
import com.dariusz.fakegpsdetector.presentation.components.common.LoadingComponent
import com.dariusz.fakegpsdetector.utils.ErrorHandling.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

object ResultUtils {

    fun <T> Flow<T>.asResult(
        viewModelScope: CoroutineScope,
        initialValue: Result<T> = Result.Loading
    ): StateFlow<Result<T>> {
        return this
            .map<T, Result<T>> {
                Result.Success(it)
            }
            .onStart { emit(Result.Loading) }
            .catch {
                emit(Result.Error(it))
                it.logError()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = initialValue
            )
    }

    @Composable
    fun <T> State<Result<T>>.showOnScreen(content: @Composable (T) -> Unit) = when (value) {
        is Result.Success<T> -> {
            content((value as Result.Success<T>).data)
        }
        is Result.Error -> {
            CenteredText("Error: ${(value as Result.Error).throwable.localizedMessage}")
        }
        else -> LoadingComponent()
    }


}