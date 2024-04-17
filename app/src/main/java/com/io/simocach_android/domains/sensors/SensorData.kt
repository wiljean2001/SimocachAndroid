package com.io.simocach_android.domains.sensors

import android.util.Log
import com.io.simocach_android.domains.sensors.data.SensorDataSerializer
import com.io.simocach_android.domains.sensors.data.WebSocketClient
import com.io.simocach_android.domains.sensors.packets.SensorPacketConfig
import com.io.simocach_android.domains.sensors.packets.SensorPacketsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket
import java.net.UnknownHostException
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class SensorData {

    companion object {
        private var sSensorData: SensorData? = null
        private val lock = Any()

        fun getInstance(): SensorData {
            synchronized(lock) {
                if (sSensorData == null) {
                    sSensorData = SensorData()
                }
                return sSensorData!!
            }
        }
    }

    private val timer = Timer()

    // Modificar la función para aceptar un objeto MySensor
    fun startSimulatedSensorUpdates(mySensor: MySensor, sensorConfig: SensorPacketConfig) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                simulateSensorUpdate(sensorConfig, mySensor)
            }
        }, 0, 1000) // Actualiza cada segundo (ajusta según tus necesidades)
    }

    // Función para detener la simulación
    fun stopSimulatedSensorUpdates() {
        timer.cancel()
    }

    // Función para simular datos del sensor
    fun simulateSensorUpdate(sensorConfig: SensorPacketConfig, mySensor: MySensor) {

//        val fakeSensorData = getFakeSensorData()
        try {
            val latestSensorData = WebSocketClient.getInstance().getLatestSensorData()

            latestSensorData?.let {
                // Actualizar siempre que el sensor se actualice
                val sensorEvent = parseSensorData(
                    it, // data,
                    sensorConfig,
                    MySensorEvent(),
                    sensorConfig.sensorType,
                    mySensor
                )

                // Emite el evento al flujo de paquetes del sensor correspondiente
                SensorPacketsProvider.getInstance().onSensorChanged(sensorEvent)

            }
        } catch (e: Exception){
            Log.e("SensorData", "Error on instance getLatestSensorData")
        }



    }

    private fun parseSensorData(
//        data: String,
        data: SensorDataSerializer,
        mSensorConfig: SensorPacketConfig,
        sensorEvent: MySensorEvent,
        sensorType: Int,
        mySensor: MySensor
    ): MySensorEvent? {
        // Analiza el mensaje para obtener los valores de temperatura, turbidez, TDS y pH
//        val temperatura = extractValue(data, "TEMP:")
//        val turbidez = extractValue(data, "TURB:")
//        val tds = extractValue(data, "TDS:")
//        val ph = extractValue(data, "PH:")

        when (mSensorConfig.sensorType) {

            MySensor.TYPE_TEMPERATURE -> {
                return MySensorEvent().apply {
                    accuracy = sensorEvent.accuracy
                    firstEventAfterDiscontinuity = sensorEvent.firstEventAfterDiscontinuity
                    this.sensor = mySensor
                    timestamp = sensorEvent.timestamp
                    values =
                        floatArrayOf(data.temperature)  // Actualiza los valores según el tipo de sensor
                }
            }

            MySensor.TYPE_TURBIDITY -> {
                return MySensorEvent().apply {
                    accuracy = sensorEvent.accuracy
                    firstEventAfterDiscontinuity = sensorEvent.firstEventAfterDiscontinuity
                    this.sensor = mySensor
                    timestamp = sensorEvent.timestamp
                    values =
                        floatArrayOf(data.turbidity)  // Actualiza los valores según el tipo de sensor
                }
            }

            MySensor.TYPE_TDS -> {
                return MySensorEvent().apply {
                    accuracy = sensorEvent.accuracy
                    firstEventAfterDiscontinuity = sensorEvent.firstEventAfterDiscontinuity
                    this.sensor = mySensor
                    timestamp = sensorEvent.timestamp
                    values =
                        floatArrayOf(data.tds)  // Actualiza los valores según el tipo de sensor
                }
            }

            MySensor.TYPE_PH -> {
                return MySensorEvent().apply {
                    accuracy = sensorEvent.accuracy
                    firstEventAfterDiscontinuity = sensorEvent.firstEventAfterDiscontinuity
                    this.sensor = mySensor
                    timestamp = sensorEvent.timestamp
                    values = floatArrayOf(data.ph)  // Actualiza los valores según el tipo de sensor
                }
            }
        }
        return null
    }

//    private fun extractValue(data: String, label: String): Float {
//        val start = data.indexOf(label) + label.length
//        val end = data.indexOf(',', start).takeIf { it != -1 } ?: data.length
//        val value = data.substring(start, end)
//        return value.toFloatOrNull() ?: 0f
//    }


    private fun getFakeSensorData(): String {
        // Genera datos simulados para cada tipo de sensor
        val temperatura = generateFakeValueTemperature("TEMP:")
        val turbidez = generateFakeValue("TURB:")
        val tds = generateFakeValue("TDS:")
        val ph = generateFakeValue("PH:")

        return "TEMP:$temperatura,TURB:$turbidez,TDS:$tds,PH:$ph"
    }

    private fun generateFakeValueTemperature(label: String): Float {
        // Genera un valor aleatorio para simular lecturas del sensor
        val random = Random()
        return 25.0f + random.nextFloat() * 2.0f  // Ajusta según el rango necesario
    }

    private fun generateFakeValue(label: String): Float {
        // Genera un valor aleatorio para simular lecturas del sensor
        val random = Random()
        return random.nextFloat() * 2.0f  // Ajusta según el rango necesario
    }
}