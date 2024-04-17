package com.io.simocach_android.domains.sensors.provider

import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.SensorsConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SensorsProvider {

    companion object {
        private var sSensorsProvider: SensorsProvider? = null

        private val lock = Any()

        fun getInstance(): SensorsProvider {
            synchronized(lock) {
                if (sSensorsProvider == null) {
                    sSensorsProvider = SensorsProvider()
                }
                return sSensorsProvider!!
            }
        }

    }

    private var mSensors: List<ModelSensor> = mutableListOf()
    private val _mSensorsFlow = MutableSharedFlow<List<ModelSensor>>(replay = 0)

    val mSensorsFlow = _mSensorsFlow.asSharedFlow()

    var mDefaultScope = CoroutineScope(Job() + Dispatchers.Default)

//    fun setSensorManager(): SensorsProvider { //manager: SensorManager
////        mSensorManager = manager
//        return this
//    }


//    fun listenSensors(): SensorsProvider {


//        Log.d("SensorsProvider","listenSensors: ")
//        if(mSensors.isEmpty()){
//            // ¿Cómo hacer esto?
////            val sensorList = MySensor.getSensorList(MySensor.TYPE_ALL).filter {
//////               SensorsConstants.MAP_TYPE_TO_AXIS_COUNT
////                SensorsConstants.SENSORS.contains(it.type)
////
////            }.distinctBy { it.type }.toList()
//
//            // Intentando algo
////            var sensorList = SensorsConstants.SENSORS.distinctBy { it  }.toList()
////            Log.d("SensorProvider", "$sensorList")
//
//            // Necesario:
//            mSensors = sensorList.map { ModelSensor(it.type, it) }.toList()
//
//        }

        // En tu función listenSensors() en SensorsProvider
//
//        mDefaultScope.launch {
////            Log.d("SensorsProvider","listenSensors 2: ${mSensors.size}")
//            _mSensorsFlow.emit(mSensors)
//
//        }
//        return this
////        sensorList[0].
//    }

    fun listenSensors(): SensorsProvider {
        if (mSensors.isEmpty()) {
            // Obtén la lista de sensores utilizando la nueva función en SensorsConstants
            val sensorList = SensorsConstants.getSensorList(MySensor.TYPE_ALL)
                .filter { SensorsConstants.SENSORS.contains(it.type) }
                .distinctBy { it }

            // Mapéalo a instancias de ModelSensor
            mSensors = sensorList.map { ModelSensor(it.type, it) }.toList()
        }

        mDefaultScope.launch {
            _mSensorsFlow.emit(mSensors)
        }
        return this
    }

    fun listenSensor(sensorType: Int): Flow<ModelSensor?> {

        var flow=  mSensorsFlow.map { sensors -> return@map sensors.singleOrNull { modelSensor ->
            modelSensor.type == sensorType }  }

        return flow
    }

    fun getSensor(sensorType: Int): ModelSensor? {

        return mSensors.singleOrNull { modelSensor ->
            modelSensor.type == sensorType
        }
    }

    fun clearAll() {

    }

}