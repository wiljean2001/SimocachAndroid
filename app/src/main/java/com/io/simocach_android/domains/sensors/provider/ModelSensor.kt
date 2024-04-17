package com.io.simocach_android.domains.sensors.provider

import android.util.Log
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.SensorsConstants

class ModelSensor(
    var type: Int = -1,
    var sensor: MySensor? = null,
    var info: Map<String, Any> = mutableMapOf(),
    var name: String = ""

) {

    init {
        if (sensor != null) {
            name = SensorsConstants.MAP_TYPE_TO_NAME.get(type, sensor?.getSensorName(type) ?: "")
            Log.d("ModelSensor", "Name: " + name)
            if (info is MutableMap<String, Any>) {
                /*sensorType =  sensor.getType();*/

                (info as MutableMap<String, Any>)[SensorsConstants.DETAIL_KEY_NAME] =
                    sensor!!.getSensorName(type)
                (info as MutableMap<String, Any>)[SensorsConstants.DETAIL_KEY_VERSION] =
                    sensor!!.getSensorVersion(type)
                (info as MutableMap<String, Any>)[SensorsConstants.DETAIL_KEY_S_TYPE] =
                    sensor!!.getSensorType(type)

            }
        }
    }
}