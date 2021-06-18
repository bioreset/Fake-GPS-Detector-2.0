package com.dariusz.fakegpsdetector.data.source.local.sensor.requirements

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.dariusz.fakegpsdetector.model.PermissionStatusModel

class PermissionStatusLiveData(
    private var context: Context,
    private var permissionsToListen: List<String>
) : LiveData<PermissionStatusModel>() {

    private fun isPermissionGranted(): Boolean {
        return permissionsToListen.all {
            ActivityCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onActive() = handlePermissionCheck()

    private fun handlePermissionCheck() {
        if (isPermissionGranted())
            postValue(PermissionStatusModel(status = true))
        else
            postValue(PermissionStatusModel(status = false))
    }
}
