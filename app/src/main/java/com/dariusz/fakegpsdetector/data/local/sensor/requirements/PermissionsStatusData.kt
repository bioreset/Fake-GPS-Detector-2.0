package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import javax.inject.Inject

interface PermissionsStatusData {

    fun getLivePermissionStatus(permissionsToListen: List<String>): PermissionStatusModel

}

class PermissionsStatusDataImpl
@Inject
constructor(
    private val context: Context
) : PermissionsStatusData {

    override fun getLivePermissionStatus(permissionsToListen: List<String>): PermissionStatusModel =
        context.livePermissionsStatus(permissionsToListen)

    private fun Context.livePermissionsStatus(permissionsToListen: List<String>): PermissionStatusModel =
        handlePermissionCheck(checkPermissions(applicationContext, permissionsToListen))

    private fun handlePermissionCheck(status: Boolean): PermissionStatusModel {
        return if (status)
            PermissionStatusModel(status = true)
        else
            PermissionStatusModel(status = false)
    }

    private fun checkPermissions(context: Context, permissionsToListen: List<String>): Boolean {
        return permissionsToListen.all {
            ActivityCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
