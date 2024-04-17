package com.io.simocach_android.ui.pages.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.io.simocach_android.domains.chart.mpchart.MpChartDataManager
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.SensorData
import com.io.simocach_android.domains.sensors.packets.SensorPacketConfig
import com.io.simocach_android.domains.sensors.packets.SensorPacketsProvider
import com.io.simocach_android.domains.sensors.provider.SensorsProvider
import com.io.simocach_android.ui.pages.home.model.ModelHomeSensor
import com.io.simocach_android.ui.pages.home.state.HomeUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private var mSensors: MutableList<ModelHomeSensor> = mutableListOf()

    // Game UI state
    private val _uiState = MutableStateFlow(HomeUiState())

    // Backing property to avoid state updates from other classes
    val mUiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _mSensorsList = mutableStateListOf<ModelHomeSensor>()
    val mSensorsList: SnapshotStateList<ModelHomeSensor> = _mSensorsList


    private val _mActiveSensorListFlow = MutableStateFlow<MutableList<ModelHomeSensor>>(
        mutableListOf()
    )
    val mActiveSensorListFlow: StateFlow<MutableList<ModelHomeSensor>> = _mActiveSensorListFlow
    private val _mActiveSensorList = mutableListOf<ModelHomeSensor>()

    private val mIsActiveMap = mutableMapOf<Int, Boolean>(
        Pair(MySensor.TYPE_TEMPERATURE, true),
        Pair(MySensor.TYPE_TURBIDITY, true),
        Pair(MySensor.TYPE_TDS, true),
        Pair(MySensor.TYPE_PH, true)
    )

    //    TODO use this in future private val mSensorPacketsMap = mutableMapOf<Int, ModelSensorPacket>()
    private val mChartDataManagerMap = mutableMapOf<Int, MpChartDataManager>()

    init {

        viewModelScope.launch {

            SensorsProvider.getInstance().mSensorsFlow.map { value ->
//                Log.d("HomeViewModel","${this@HomeViewModel} init sensors active  1: $mIsActiveMap")
                value.map {
                    ModelHomeSensor(
                        it.type,
                        it.sensor,
                        it.info,
                        0f,
                        mIsActiveMap.getOrDefault(it.type, false)
                    )
                }.toMutableList()
            }.collectLatest {
                mSensors = it
//                Log.d("HomeViewModel","${this@HomeViewModel} init sensors active  1: $mIsActiveMap")
//                Log.d("HomeViewModel", "sensors 2: $it")
                if (_mSensorsList.size == 0) {
                    _mSensorsList.addAll(mSensors)
                    val activeSensors = it.filter { modelHomeSensor -> modelHomeSensor.isActive }
//                     _mActiveSensorStateList.addAll(activeSensors)
                    _mActiveSensorList.addAll(activeSensors)
                    _mActiveSensorListFlow.emit(_mActiveSensorList)
                    getInitialChartData()
                    initializeFlow()
                }

            }

        }
        // Initialice listen sensors
        SensorsProvider.getInstance().listenSensors()

    }

    private fun getInitialChartData() {
        for (sensor in _mActiveSensorList) {
//            Log.d("HomeViewModel", "getInitialChartData")
//            Inicialice data charts
            getChartDataManager(sensor.type)
        }

    }

    private fun initializeFlow() {

        val sensorPacketFlow =
            SensorPacketsProvider.getInstance().mSensorPacketFlow


        for (sensor in _mActiveSensorList) {
            attachPacketListener(sensor);
        }

        // TODO: Primero validar si el socket de SensorData no es nulla para ejecutar:
        SensorPacketsProvider.getInstance().startSensorSimulation(_mActiveSensorList)

        viewModelScope.launch {
            sensorPacketFlow.collect {
//                mLogTimestamp = it.timestamp
//                Log.e("HomeViewModel", "sensorPacketFlow -> type: ${it.type}")
                mChartDataManagerMap[it.type]?.addEntry(it)
            }
        }

//        Log.e("HomeViewModel", "mChartDataManagerMap.size: ${mChartDataManagerMap.size}")

        mChartDataManagerMap.forEach { (_, mpChartDataManager) ->
            viewModelScope.launch {
                mpChartDataManager.runPeriodically()
            }
        }
    }
//
    private fun attachPacketListener(sensor: ModelHomeSensor) {
        SensorPacketsProvider.getInstance().attachSensor(
            SensorPacketConfig(sensor.type, 2)
        )
    }

    private fun detachPacketListener(sensor: ModelHomeSensor) {
        SensorPacketsProvider.getInstance().detachSensor(
            sensor.type
        )
    }

    fun onSensorChecked(type: Int, isChecked: Boolean) {
        val isCheckedPrev = mIsActiveMap.getOrDefault(type, false)

        if (isCheckedPrev != isChecked) {
            mIsActiveMap[type] = isChecked
        }

        val index = mSensors.indexOfFirst { it.type == type }
        if (index >= 0) {
//            Log.d("HomeViewModel", "onSensorChecked: Index: $index $isChecked")
            val sensor = mSensors[index]
            val updatedSensor =
                ModelHomeSensor(sensor.type, sensor.sensor, sensor.info, sensor.valueRms, isChecked)
            mSensors[index] = updatedSensor

            mSensorsList[index] = updatedSensor
            updateActiveSensor(updatedSensor, isChecked)

        }
        /*viewModelScope.launch {
            emitUiState()

        }*/

    }

    private fun updateActiveSensor(sensor: ModelHomeSensor, isChecked: Boolean = false) {
        val index = _mActiveSensorList.indexOfFirst { it.type == sensor.type }

        if (!isChecked && index >= 0) {
            val manager = mChartDataManagerMap.remove(sensor.type)
            manager?.destroy()
//            _mActiveSensorStateList.removeAt(index)
             detachPacketListener(sensor)


            _mActiveSensorList.removeAt(index)
            viewModelScope.launch {

                _mActiveSensorListFlow.emit(_mActiveSensorList)
            }

        } else if (isChecked && index < 0) {
//            _mActiveSensorStateList.add(sensor)

            _mActiveSensorList.add(sensor)
//     ->       attachPacketListener(sensor)
            viewModelScope.launch {

                _mActiveSensorListFlow.emit(_mActiveSensorList)
            }
            getChartDataManager(type = sensor.type).runPeriodically()
        }
    }


    fun getChartDataManager(type: Int): MpChartDataManager {
        val chartDataManager = mChartDataManagerMap.getOrPut(type, defaultValue = {
            MpChartDataManager(type, onDestroy = {
            })
        })
//        Log.d("HomeViewModel", "getChartDataManager: $type")
        return chartDataManager
    }


    fun setActivePage(page: Int?) {

        viewModelScope.launch {
//            Log.e("HomeViewModel", "page: $page | sensorsList ${_mActiveSensorList.size}")
            if (page != null && _mActiveSensorList.size > 0) {
                if (_mActiveSensorList.size > page) {
                    val sensor = _mActiveSensorList[page]
//                _mUiCurrentSensorState.emit(sensor)
//                    Log.e("HomeViewModel", "_mActiveSensorList.size > page: sensor $sensor")

                    _uiState.emit(
                        _uiState.value.copy(
                            currentSensor = sensor,
                            activeSensorCounts = _mActiveSensorList.size
                        )
                    )
                } else {
//                    Log.d("HomeViewModel", "_mActiveSensorList.size < page")
                    val sensor = _mActiveSensorList[_mActiveSensorList.size - 1]
                    _uiState.emit(
                        _uiState.value.copy(
                            currentSensor = sensor,
                            activeSensorCounts = _mActiveSensorList.size
                        )
                    )
                }

            } else {
//                Log.e("HomeViewModel", "page != null && _mActiveSensorList.size > 0")
//                _mUiCurrentSensorState.emit(null)
                _uiState.emit(
                    _uiState.value.copy(
                        currentSensor = null,
                        activeSensorCounts = _mActiveSensorList.size
                    )
                )

            }
        }

    }

    override fun onCleared() {
        super.onCleared()

//        Log.d("HomeViewModel", "onCleared")
        mChartDataManagerMap.forEach { (_, mpChartDataManager) -> mpChartDataManager.destroy() }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel() as T
        }
    }


}