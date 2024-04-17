package com.io.simocach_android.domains.sensors

interface MySensorEventListener {

    //
//    fun onSensorDataReceived(sensorPacket: ModelSensorPacket)
    //
    fun onAccuracyChanged(p0: MySensor?, p1: Int)

    /**
     * @param p0 MySensorEvent
     */
    fun onSensorChanged(p0: MySensorEvent?)

}

class MySensorEvent {
    var accuracy = 0
    var firstEventAfterDiscontinuity = false
    var sensor: MySensor? = null
    var timestamp: Long = 0
    var values = FloatArray(0)

}


class MySensor(
    val type: Int,
    val name: String,
    val info: Map<String, Any> = emptyMap()
) {

    companion object {
        //        Keys for sensors
        val TYPE_ALL = -1
        val TYPE_TEMPERATURE = 1
        val TYPE_TURBIDITY = 2
        val TYPE_TDS = 3
        val TYPE_PH = 4

        // Names
        val sensorNames = mapOf(
            TYPE_TEMPERATURE to "Temperatura",
            TYPE_TURBIDITY to "Turbidez",
            TYPE_TDS to "TDS",
            TYPE_PH to "PH"
        )
        // Versions
        val sensorVersions = mapOf(
            TYPE_TEMPERATURE to "1.0",
            TYPE_TURBIDITY to "1.0",
            TYPE_TDS to "1.0",
            TYPE_PH to "1.0"
        )
        // Types of sensors; This is not type for index
        val sensorTypes = mapOf(
            TYPE_TEMPERATURE to "DS18B20",
            TYPE_TURBIDITY to "Gravity turbidity Sensor",
            TYPE_TDS to "SEN0244 - Analog TDS Sensor",
            TYPE_PH to "Gravity Ph Sensor"
        )

        // Agregar una función para obtener el nombre del sensor
        fun getSensorName(type: Int): String {
            return sensorNames[type] ?: "Desconocido"
        }
    }

    fun getSensorName(type: Int): String {
        return sensorNames[type] ?: "Desconocido"
    }

    fun getSensorVersion(type: Int): String {
        return sensorVersions[type] ?: "Desconocido"
    }

    fun getSensorType(type: Int): String {
        return sensorTypes[type] ?: "Desconocido"
    }

    // Función para obtener la información adicional de un sensor
    fun getAdditionalInfo(): Map<String, Any> {
        return info
    }
}
