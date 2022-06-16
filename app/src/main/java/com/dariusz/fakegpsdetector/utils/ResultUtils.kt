package com.dariusz.fakegpsdetector.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.dariusz.fakegpsdetector.domain.model.Result
import com.dariusz.fakegpsdetector.presentation.components.common.CenteredText
import com.dariusz.fakegpsdetector.presentation.components.common.LoadingComponent
import com.dariusz.fakegpsdetector.utils.ErrorHandling.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

object ResultUtils {

    fun <T> Flow<T>.asResult(
        coroutineScope: CoroutineScope
    ): StateFlow<Result<T>> {
        return this
            .map<T, Result<T>> {
                Log.d("flow-data-result", it.toString())
                Result.Success(it)
            }
            .catchAndEmit {
                Result.Error(it)
            }
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = Result.Loading
            )
    }

    @Composable
    fun <T> State<Result<T>>.showOnScreen(content: @Composable (T) -> Unit) = when (value) {
        is Result.Success<T> -> {
            content((value as Result.Success<T>).data)
        }
        is Result.Error -> {
            CenteredText("Error: ${(value as Result.Error).throwable}")
        }
        is Result.Loading -> {
            LoadingComponent()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private inline fun <T> Flow<T>.catchAndEmit(crossinline action: (Throwable) -> Unit): Flow<T> =
        flatMapLatest {
            flow { emit(it) }
                .catch {
                    it.logError()
                    action(it)
                }
        }


}
