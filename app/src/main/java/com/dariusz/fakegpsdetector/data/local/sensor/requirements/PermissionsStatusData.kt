package com.dariusz.fakegpsdetector.data.local.sensor.requirements

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.dariusz.fakegpsdetector.domain.model.PermissionStatusModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PermissionsStatusData {

    fun getPermissionStatus(permissionsToListen: List<String>): Flow<PermissionStatusModel>

}

class PermissionsStatusDataImpl
@Inject
constructor(
    @ApplicationContext private val context: Context
) : PermissionsStatusData {

    override fun getPermissionStatus(permissionsToListen: List<String>): Flow<PermissionStatusModel> =
        context.livePermissionsStatus(permissionsToListen)

    private fun Context.livePermissionsStatus(permissionsToListen: List<String>): Flow<PermissionStatusModel> =
        flow { emit(checkPermissions(applicationContext, permissionsToListen)) }

    private fun checkPermissions(
        context: Context,
        permissionsToListen: List<String>
    ): PermissionStatusModel {
        val missingPermissions: MutableList<String> = mutableListOf()

        permissionsToListen.forEach {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            )
                missingPermissions.add(it)
        }

        return if (missingPermissions.size == 0)
            PermissionStatusModel(true)
        else
            PermissionStatusModel(false, missingPermissions)
    }

}
