package com.io.simocach_android.domains.chart.mpchart

import android.util.Log
import androidx.compose.ui.graphics.toArgb
import com.io.simocach_android.domains.chart.ChartDataHandler
import com.io.simocach_android.domains.chart.entity.ModelChartUiUpdate
import com.io.simocach_android.domains.chart.entity.ModelLineChart
import com.io.simocach_android.domains.sensors.SensorsConstants
import com.io.simocach_android.domains.sensors.packets.ModelSensorPacket
import com.io.simocach_android.ui.resources.values.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MpChartDataManager(
    var sensorType: Int,
    var mSensorDelayType: Int = 2,
    var onDestroy: (type: Int) -> Unit = {}
) {

    private var mIsRunningPeriod: Boolean = false
    var mDataComputationScope = CoroutineScope(Job() + Dispatchers.Default)

    private var mChartDataHandler: ChartDataHandler

    val mSensorPacketFlow: SharedFlow<ModelChartUiUpdate>

    init {

        mChartDataHandler = ChartDataHandler(sensorType)
        var axisCount = SensorsConstants.MAP_TYPE_TO_AXIS_COUNT.get(sensorType)

        if (axisCount == 1) {
            mChartDataHandler.addDataSet(
                SensorsConstants.DATA_AXIS_VALUE,
                Colors.NotePink.toArgb(),
                SensorsConstants.DATA_AXIS_VALUE_STRING, emptyArray(), false
            )

        } else if (axisCount == 3) {
            mChartDataHandler.addDataSet(
                SensorsConstants.DATA_AXIS_X,
                Colors.NotePink.toArgb(),
                SensorsConstants.DATA_AXIS_X_STRING, emptyArray(), false
            )
            mChartDataHandler.addDataSet(
                SensorsConstants.DATA_AXIS_Y,
                Colors.SensifyGreen40.toArgb(),
                SensorsConstants.DATA_AXIS_Y_STRING, emptyArray(), false
            )
            mChartDataHandler.addDataSet(
                SensorsConstants.DATA_AXIS_Z,
                Colors.CHART_3.toArgb(),
                SensorsConstants.DATA_AXIS_Z_STRING, emptyArray(), false
            )
        }

        mSensorPacketFlow = mChartDataHandler.mSensorPacketFlow

    }

    fun destroy() {
        Log.d("MpChartDataManager", "destroy $sensorType")
        mChartDataHandler.destroy()
        onDestroy.invoke(sensorType)
    }

    fun setSensorDelayType(type: Int) {
        mSensorDelayType = type
    }

    fun runPeriodically() {


        if (mIsRunningPeriod) {
            return
        }
        mIsRunningPeriod = true

        mDataComputationScope.launch {
            delay(100)
            Log.d("MpChartViewManager ", "createChart periodic Task: $sensorType")
            mChartDataHandler.runPeriodicTask()
        }

//        return lineChart

    }


    fun addEntry(sensorPacket: ModelSensorPacket) {
        mChartDataHandler.addEntry(sensorPacket)
    }

    fun getModel(): ModelLineChart {
        return mChartDataHandler.mModelLineChart
    }


}