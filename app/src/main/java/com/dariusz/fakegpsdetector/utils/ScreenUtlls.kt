package com.dariusz.fakegpsdetector.utils

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.composeViewModel
import kotlinx.coroutines.flow.StateFlow

object ScreenUtlls {

    @Composable
    inline fun <reified VM : ViewModel, T> ShowScreen(
        crossinline viewModel: (Context) -> VM,
        data: (VM) -> StateFlow<T>,
        composable: @Composable (T) -> Unit,
        crossinline launchedEffect: (VM) -> Unit
    ) {

        val context = LocalContext.current

        val coroutineScope = rememberCoroutineScope()

        val initVM = composeViewModel {
            viewModel.invoke(context)
        }

        val rememberData by remember(initVM) {
            data.invoke(initVM)
        }.collectAsState()

        composable.invoke(rememberData)

        LaunchedEffect(coroutineScope) {
            launchedEffect.invoke(initVM)
        }

    }

}