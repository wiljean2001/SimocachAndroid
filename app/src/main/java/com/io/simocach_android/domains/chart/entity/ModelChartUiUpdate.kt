package com.io.simocach_android.domains.chart.entity

import com.io.simocach_android.domains.sensors.packets.ModelSensorPacket

data class ModelChartUiUpdate(
    var sensorType: Int,
    var size: Int,
    var packets: List<ModelSensorPacket> = listOf(),
    var timestamp: Long = System.currentTimeMillis()
) {
}