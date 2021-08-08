package com.dariusz.fakegpsdetector.utils

import androidx.compose.runtime.Composable
import com.dariusz.fakegpsdetector.domain.model.ResultState
import com.dariusz.fakegpsdetector.presentation.components.common.CenteredText
import com.dariusz.fakegpsdetector.presentation.components.common.LoadingComponent

object ResultUtils {

    @Composable
    fun <T> ManageResultOnScreen(
        input: ResultState<T>,
        content: @Composable (T) -> Unit,
    ) {
        when (input) {
            is ResultState.Loading -> {
                LoadingComponent()
            }
            is ResultState.Success -> {
                content.invoke(input.data)
            }
            is ResultState.Error -> {
                CenteredText("Error: ${input.throwable}")
            }
            is ResultState.Idle -> {
                //default option; do nothing
            }
        }
    }

    @Composable
    fun <T, R, P> ManageResultsOnScreen(
        inputOne: ResultState<T>,
        inputTwo: ResultState<R>? = null,
        inputThree: ResultState<P>? = null,
        content: @Composable (T, R?, P?) -> Unit,
    ) {
        when (inputOne) {
            is ResultState.Loading -> {
                LoadingComponent()
            }
            is ResultState.Success -> {
                when (inputTwo) {
                    is ResultState.Loading -> {
                        LoadingComponent()
                    }
                    is ResultState.Success -> {
                        if (inputThree == null) {
                            content.invoke(
                                inputOne.data,
                                inputTwo.data,
                                null
                            )
                        } else {
                            when (inputThree) {
                                is ResultState.Loading -> {
                                    LoadingComponent()
                                }
                                is ResultState.Success -> {
                                    content.invoke(
                                        inputOne.data,
                                        inputTwo.data,
                                        inputThree.data
                                    )
                                }
                                is ResultState.Error -> {
                                    CenteredText("Error: ${inputThree.throwable}")
                                }
                                is ResultState.Idle -> {
                                    //default option; do nothing
                                }
                            }
                        }
                    }
                    is ResultState.Error -> {
                        CenteredText("Error: ${inputTwo.throwable}")
                    }
                    is ResultState.Idle -> {
                        //default option; do nothing
                    }
                }
            }
            is ResultState.Error -> {
                CenteredText("Error: ${inputOne.throwable}")
            }
            is ResultState.Idle -> {
                //default option; do nothing
            }
        }
    }

}