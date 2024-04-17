package com.io.simocach_android.ui.pages.home.model

import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.SensorsConstants

data class ModelHomeSensor(
    var type: Int = -1,
    var sensor: MySensor? = null,
    var info: Map<String, Any> = mutableMapOf(),
    var valueRms: Float? = 0.0f,
    var isActive:  Boolean =  false,
    var name: String = ""
) {

    init {
        name = SensorsConstants.MAP_TYPE_TO_NAME.get( type, MySensor.getSensorName(type)?: "")
    }
}