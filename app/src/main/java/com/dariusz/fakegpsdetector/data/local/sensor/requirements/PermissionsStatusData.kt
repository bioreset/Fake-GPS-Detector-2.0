package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

interface PermissionsStatusData {

    fun getLivePermissionStatus(permissionsToListen: List<String>): Flow<PermissionStatusModel>

}

class PermissionsStatusDataImpl
@Inject
constructor(
    private val context: Context
) : PermissionsStatusData {

    override fun getLivePermissionStatus(permissionsToListen: List<String>): Flow<PermissionStatusModel> =
        context.livePermissionsStatus(permissionsToListen)

    private fun Context.livePermissionsStatus(permissionsToListen: List<String>): Flow<PermissionStatusModel> {
        val permissionStatus =
            handlePermissionCheck(checkPermissions(applicationContext, permissionsToListen))
        return flow {
            emit(permissionStatus)
        }.shareIn(
            MainScope(),
            SharingStarted.WhileSubscribed()
        )
    }

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
