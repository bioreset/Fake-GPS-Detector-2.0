package com.dariusz.fakegpsdetector.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dariusz.fakegpsdetector.domain.model.ResultState
import com.dariusz.fakegpsdetector.utils.ErrorHandling.displayError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

object ViewModelsUtils {

    @Suppress("UNCHECKED_CAST")
    @Composable
    inline fun <reified VM : ViewModel> composeViewModel(crossinline viewModel: () -> VM): VM =
        viewModel(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModel.invoke() as T
            }
        })


    fun <T> manageResult(
        mutableInput: MutableStateFlow<ResultState<T>>,
        dataFromAction: T
    ) = mutableInput.getResultOfGenericResponse(dataFromAction)

    private fun <T> MutableStateFlow<ResultState<T>>.getResultOfGenericResponse(
        data: T
    ) {
        value = ResultState.Idle
        value = try {
            ResultState.Success(data)
        } catch (throwable: Throwable) {
            ResultState.Error(throwable).also {
                throwable.displayError()
            }
        }
    }

    suspend fun <T> Flow<T>.collectState(
        mutableInput: MutableStateFlow<ResultState<T>>
    ) = collect {
        mutableInput.value = asResultState(it).value
    }

    private fun <T> asResultState(data: T): MutableStateFlow<ResultState<T>> {
        return MutableStateFlow(ResultState.Success(data))
    }


    private val ViewModel.ioTask
        get() = viewModelScope + Dispatchers.IO

    fun ViewModel.launchVMTask(
        action: suspend () -> Unit
    ) = ioTask.launch {
        action.invoke()
    }


}