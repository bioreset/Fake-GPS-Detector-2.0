package com.dariusz.fakegpsdetector.presentation.screens.infoscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dariusz.fakegpsdetector.domain.repository.LocationFromApiResponseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.P)
@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class InfoScreenViewModelFactory
@Inject
constructor(
    private val locationFromApiResponseRepository: LocationFromApiResponseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoScreenViewModel(
            locationFromApiResponseRepository
        ) as T
    }
}