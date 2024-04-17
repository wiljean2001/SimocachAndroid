package com.io.simocach_android.domains.sensors.packets

data class SensorPacketConfig(
    var sensorType: Int,
    var sensorDelay: Int = 2 // default delay
) {
}