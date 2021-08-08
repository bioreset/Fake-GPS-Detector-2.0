package com.dariusz.fakegpsdetector.presentation.components.common

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.dariusz.fakegpsdetector.R
import com.dariusz.fakegpsdetector.di.RepositoryModule.provideRequirementsRepository
import com.dariusz.fakegpsdetector.presentation.MainViewModel
import com.dariusz.fakegpsdetector.utils.Constants.permissionsToWatch
import com.dariusz.fakegpsdetector.utils.ResultUtils.ManageResultOnScreen
import com.dariusz.fakegpsdetector.utils.ViewModelsUtils.composeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Composable
fun MainAlertBox() {

    val currentContext = LocalContext.current

    val viewModel = composeViewModel {
        MainViewModel(
            provideRequirementsRepository(currentContext)
        )
    }

    val currentGPSStatus by remember(viewModel) { viewModel.gpsStatus }.collectAsState()
    val currentWifiStatus by remember(viewModel) { viewModel.wifiStatus }.collectAsState()
    val currentPermissionsStatus by remember(viewModel) { viewModel.permissionsStatus }.collectAsState()

    ManageResultOnScreen(currentGPSStatus) {
        if (!it.status) GpsAlert(currentContext)
    }
    ManageResultOnScreen(currentWifiStatus) {
        if (!it.status) WifiAlert(currentContext)
    }
    ManageResultOnScreen(currentPermissionsStatus) {
        if (!it.status) PermissionsAlert(currentContext)
    }

}

@Composable
fun WifiAlert(currentContext: Context) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = currentContext.getString(R.string.wifi_required_title)) },
            text = { Text(currentContext.getString(R.string.wifi_required_body)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val intent = Intent()
                        intent.action = Settings.ACTION_WIFI_SETTINGS
                        currentContext.startActivity(intent)
                    }
                ) {
                    Text("Ok")
                }
            },
        )
    }
}

@Composable
fun GpsAlert(currentContext: Context) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = currentContext.getString(R.string.gps_required_title)) },
            text = { Text(currentContext.getString(R.string.gps_required_body)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val intent = Intent()
                        intent.action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        currentContext.startActivity(intent)
                    }
                ) {
                    Text("Ok")
                }
            },
        )
    }
}

@Composable
fun PermissionsAlert(currentContext: Context) {
    val openDialog = remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        openDialog.value = isGranted.containsValue(false)
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = currentContext.getString(R.string.gps_required_title)) },
            text = { Text(currentContext.getString(R.string.gps_required_body)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        launcher.launch(
                            permissionsToWatch.toTypedArray()
                        )
                    }
                ) {
                    Text("Ok")
                }
            },
        )
    }
}
