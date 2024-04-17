package com.io.simocach_android.domains.sensors.packets

import android.util.Log
import android.util.SparseArray
import androidx.core.util.valueIterator
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.MySensorEvent
import com.io.simocach_android.domains.sensors.MySensorEventListener
import com.io.simocach_android.domains.sensors.SensorData
import com.io.simocach_android.ui.pages.home.model.ModelHomeSensor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SensorPacketsProvider : MySensorEventListener {

    companion object {
        private var sSensorPacketsProvider: SensorPacketsProvider? = null
        private val lock = Any()

        fun getInstance(): SensorPacketsProvider {
            synchronized(lock) {
                if (sSensorPacketsProvider == null) {
                    sSensorPacketsProvider = SensorPacketsProvider()
                }
                return sSensorPacketsProvider!!
            }
        }
    }

    private var isSensorConnected: Boolean? = false
    private var mDefaultScope = CoroutineScope(Job() + Dispatchers.Default)

    private var mSensorConfigs = SparseArray<SensorPacketConfig>()

    private val _mSensorPacketFlow = MutableSharedFlow<ModelSensorPacket>(replay = 0)
    val mSensorPacketFlow = _mSensorPacketFlow.asSharedFlow()

    fun attachSensor(config: SensorPacketConfig): SensorPacketsProvider {
        val prevConfig = mSensorConfigs[config.sensorType]
        var shouldRegister = true
        if (prevConfig != null) {
            if (prevConfig.sensorType != config.sensorType) {
                unregisterSensor(config)
            } else {
                shouldRegister = false
            }
        }
        if (shouldRegister) {
//            Log.d("SensorPacketsProvider", "attachSensor 2")

            mSensorConfigs[config.sensorType] = config
            registerSensor(config)
        }
        return this
    }

    fun detachSensor(sensorType: Int): SensorPacketsProvider {
//        val sensorConfig = mSensorConfigs.find { it.sensorType == sensorType }
//        sensorConfig?.let {
//            unregisterSensor(sensorConfig)
//            mSensorConfigs.remove(sensorConfig)
//        }
//        return this
        val sensorConfig = mSensorConfigs[sensorType]
        if (sensorConfig != null) {

//            Log.d("SensorPacketsProvider", "detachSensor 2")
            unregisterSensor(sensorConfig)
            mSensorConfigs.remove(sensorType)

        }
        return this
    }

    private fun unregisterSensor(config: SensorPacketConfig) {
//        if (isSensorConnected == false) return
        Log.e("SensorPacketsProvider", "unregisterSensor(config: SensorPacketConfig)")

        // Implementa la lógica para desconectar el sensor según tu necesidad
    }

    private fun registerSensor(config: SensorPacketConfig) {
//        if (isSensorConnected == false) return
        // Implementa la lógica para conectar el sensor según tu necesidad
        Log.e("SensorPacketsProvider", "registerSensor(config: SensorPacketConfig)")
    }

    override fun onAccuracyChanged(p0: MySensor?, p1: Int) {
        TODO("Not yet implemented")
    }

    // Esta función se debe ejecutar cada vez que los datos de los sensores se actualicen
    override fun onSensorChanged(p0: MySensorEvent?) {
//        Log.e("SensorPacketProvider", "OnSensorChanged exec")
        if (p0 != null) {
            mDefaultScope.launch {
                onSensorEvent(p0)
            }
        }
    }

    private suspend fun onSensorEvent(sensorEvent: MySensorEvent) {

        synchronized(this) {
            val sensorType = sensorEvent.sensor?.type
            val sensorConfig = sensorType?.let { mSensorConfigs.get(it) }

            if (sensorConfig != null) {
                val sensorPacket = ModelSensorPacket(
                    sensorEvent,
                    sensorEvent.values,
                    sensorType,
                    sensorConfig.sensorDelay,
                    System.currentTimeMillis()
                )
//                Log.d("SensorPacketsProvider", "$sensorPacket")
                mDefaultScope.launch {
//                    Log.d("SensorPacketsProvider", "sa: ${Arrays.toString(sensorEvent.values)}")
                    _mSensorPacketFlow.emit(
                        sensorPacket
                    )

                }
            }
        }

    }

    // Agregar un nuevo método para iniciar la simulación
    fun startSensorSimulation(sensors: MutableList<ModelHomeSensor>) {

        for (sensor in sensors){
//            Log.d("SensorPacketsProvider", "Exec the for sensors ${sensor.type}")
            val sensorConfig = mSensorConfigs[sensor.type]
//            val sensorConfig = SensorPacketConfig(sensor.type)
            sensor.sensor?.let { SensorData.getInstance().startSimulatedSensorUpdates(it, sensorConfig) }
        }
    }


    fun clearAll() {
        for (sensorConfig in mSensorConfigs.valueIterator()){
//                Log.d("SensorPacketsProvider","clearAll unregisterSensor")
            unregisterSensor(sensorConfig)
        }
        mSensorConfigs.clear()
    }
}
