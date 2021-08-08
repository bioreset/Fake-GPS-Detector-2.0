package com.dariusz.fakegpsdetector.presentation.components.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.dariusz.fakegpsdetector.domain.model.CurrentTheme
import com.dariusz.fakegpsdetector.presentation.components.navigation.BottomNavigationBar
import com.dariusz.fakegpsdetector.presentation.components.navigation.MainNavigationHost
import com.dariusz.fakegpsdetector.presentation.components.theme.MainTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun FGDApp() {
    val navController = rememberNavController()
    val theme = remember {
        CurrentTheme()
    }
    val context = LocalContext.current
    MainTheme(theme) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            content = {
                MainNavigationHost(navController, context)
                MainAlertBox()
            }
        )
    }
}
