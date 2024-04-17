package com.io.simocach_android.domains.sensors.packets

import com.io.simocach_android.domains.sensors.MySensorEvent

data class ModelSensorPacket(
    /** some reference issue with this */
    var sensorEvent: MySensorEvent? = null,  // Un identificador único para el sensor
    var values: FloatArray?,
    var type: Int,
    var delay: Int,
    var timestamp: Long
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ModelSensorPacket

        if (values != null) {
            if (other.values == null) return false
            if (!values.contentEquals(other.values)) return false
        } else if (other.values != null) return false
        if (sensorEvent != other.sensorEvent) return false
        if (type != other.type) return false
        if (delay != other.delay) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sensorEvent?.hashCode() ?: 0
        result = 31 * result + (values?.contentHashCode() ?: 0)
        result = 31 * result + type
        result = 31 * result + delay
        result = 31 * result + timestamp.hashCode()
        return result
    }

}