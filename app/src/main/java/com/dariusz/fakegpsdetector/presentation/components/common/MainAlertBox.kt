package com.dariusz.fakegpsdetector.presentation.components.common

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.dariusz.fakegpsdetector.R
import com.dariusz.fakegpsdetector.domain.model.GpsStatusModel
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import com.dariusz.fakegpsdetector.domain.model.WifiStatusModel
import com.dariusz.fakegpsdetector.presentation.MainViewModel

@Composable
fun MainAlertBox() {

    val currentContext = LocalContext.current

    val viewModel: MainViewModel = hiltViewModel()

    val requirementsStatus by viewModel.requirementsStatus.collectAsState()

    ShowAlerts(
        context = currentContext,
        gpsStatusModel = requirementsStatus.gpsStatusModel,
        wifiStatusModel = requirementsStatus.wifiStatusModel,
        permissionStatusModel = requirementsStatus.permissionStatusModel
    )
}

@Composable
fun ShowAlerts(
    context: Context,
    gpsStatusModel: GpsStatusModel,
    wifiStatusModel: WifiStatusModel,
    permissionStatusModel: PermissionStatusModel
) {
    if (!gpsStatusModel.status)
        GpsAlert(context)

    if (!wifiStatusModel.status)
        WifiAlert(context)

    if (!permissionStatusModel.status)
        PermissionsAlert(context, permissionStatusModel.neededPermissions!!)
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
fun PermissionsAlert(currentContext: Context, permissionsToBeGranted: List<String>) {
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
                            permissionsToBeGranted.toTypedArray()
                        )
                    }
                ) {
                    Text("Ok")
                }
            },
        )
    }
}
