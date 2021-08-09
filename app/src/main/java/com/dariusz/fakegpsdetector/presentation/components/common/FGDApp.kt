package com.dariusz.fakegpsdetector.presentation.components.common

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.dariusz.fakegpsdetector.domain.model.CurrentTheme
import com.dariusz.fakegpsdetector.presentation.components.navigation.BottomNavigationBar
import com.dariusz.fakegpsdetector.presentation.components.navigation.MainNavigationHost
import com.dariusz.fakegpsdetector.presentation.components.theme.MainTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun FGDApp() {
    val navController = rememberNavController()
    val theme = remember { CurrentTheme() }
    MainTheme(theme) {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            content = {
                MainNavigationHost(navController)
                MainAlertBox()
            }
        )
    }
}
