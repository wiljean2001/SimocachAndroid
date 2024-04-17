package com.io.simocach_android.domains.sensors.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SensorDataSerializer(
    @SerialName("TEMP") val temperature: Float,
    @SerialName("TURB") val turbidity: Float,
    @SerialName("TDS") val tds: Float,
    @SerialName("PH") val ph: Float
)