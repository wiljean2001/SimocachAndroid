package com.io.simocach_android.ui.resources.sensors

import android.util.SparseIntArray
import com.io.simocach_android.R
import com.io.simocach_android.domains.sensors.MySensor

object SensorsIcons {

    val MAP_TYPE_TO_ICON: SparseIntArray = object : SparseIntArray() {
        init {
            put(MySensor.TYPE_TEMPERATURE, R.drawable.ic_sensor_gyroscope) //1
            put(MySensor.TYPE_TURBIDITY, R.drawable.ic_sensor_gyroscope) //1
            put(MySensor.TYPE_TDS, R.drawable.ic_sensor_gyroscope) //1
            put(MySensor.TYPE_PH, R.drawable.ic_sensor_gyroscope) //1

        }
    }

}