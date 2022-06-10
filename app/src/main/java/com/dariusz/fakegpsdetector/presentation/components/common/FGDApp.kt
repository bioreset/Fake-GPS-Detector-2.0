package com.dariusz.fakegpsdetector.presentation.components.common

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dariusz.fakegpsdetector.presentation.components.navigation.BottomNavigationBar
import com.dariusz.fakegpsdetector.presentation.components.navigation.MainNavigationHost
import com.dariusz.fakegpsdetector.presentation.components.theme.MainTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FGDApp() {
    val navController = rememberNavController()
    MainTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            content = {
                MainNavigationHost(navController)
                MainAlertBox()
            }
        )
    }
}
