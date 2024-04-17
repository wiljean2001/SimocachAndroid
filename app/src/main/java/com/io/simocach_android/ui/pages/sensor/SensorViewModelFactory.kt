package com.io.simocach_android.ui.pages.sensor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class SensorViewModelFactory(private val sensorType: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SensorViewModel(sensorType) as T
    }
}