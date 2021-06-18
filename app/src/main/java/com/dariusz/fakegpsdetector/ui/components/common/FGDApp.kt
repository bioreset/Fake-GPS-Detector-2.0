package com.dariusz.fakegpsdetector.ui.components.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dariusz.fakegpsdetector.model.CurrentTheme
import com.dariusz.fakegpsdetector.ui.MainViewModel
import com.dariusz.fakegpsdetector.ui.components.navigation.BottomNavigationBar
import com.dariusz.fakegpsdetector.ui.components.navigation.MainNavigationHost
import com.dariusz.fakegpsdetector.ui.components.theme.MainTheme
import com.dariusz.fakegpsdetector.ui.components.theme.ThemeSaver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun FGDApp() {
    val navController = rememberNavController()
    val theme by rememberSaveable(stateSaver = ThemeSaver) { mutableStateOf(CurrentTheme()) }
    val mainViewModel: MainViewModel = viewModel()
    val currentScreen by mainViewModel.currentScreen.observeAsState()
    MainTheme(theme = theme) {
        Scaffold(
            topBar = {}, // TODO: CURRENT SCREEN TITLE
            bottomBar = { BottomNavigationBar(navController) }
        ) {
            MainNavigationHost(navController = navController)
            MainAlertBox(mainViewModel)
        }
    }
}
