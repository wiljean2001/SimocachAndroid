package com.io.simocach_android.domains.chart

import android.util.Log
import com.io.simocach_android.domains.chart.entity.ModelChartUiUpdate
import com.io.simocach_android.domains.chart.entity.ModelLineChart
import com.io.simocach_android.domains.sensors.SensorsConstants
import com.io.simocach_android.domains.sensors.packets.ModelSensorPacket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class ChartDataHandler(var sensorType: Int) {

    private var mCurrentPacket: ModelSensorPacket? = null
    private var mVisibleNum = -1
    private var mLengthSample = 100

    private val mLockDataAdd = Any()

    // TODO use this in ui handler
    private var mAddDirection = ChartConstants.DIRECTION_START_END
    var mModelLineChart: ModelLineChart


    var mUIRefreshDelay = 2
    var mDataRefreshDelay = 2

    var mDataTypesIndexed = mutableListOf<Int>()

    var mPre = mutableListOf<ModelSensorPacket>()

    var mDataComputationScope = CoroutineScope(Job() + Dispatchers.Default)
    var mDataAddScope = CoroutineScope(Job() + Dispatchers.Default)

    private val _mSensorPacketFlow = MutableSharedFlow<ModelChartUiUpdate>(replay = 0)
    val mSensorPacketFlow = _mSensorPacketFlow.asSharedFlow()

    init {
        mModelLineChart = ModelLineChart(
            mLengthSample, mVisibleNum
        )
    }

    fun destroy() {
        mDataComputationScope.cancel()
        mDataAddScope.cancel()

    }


    fun addDataSet(
        dataType: Int,
        color: Int,
        label: String,
        data: Array<Float>,
        isHidden: Boolean
    ) {
        mDataTypesIndexed.add(dataType)

        // TODO check for data added
        mModelLineChart.addDataType(dataType, color, label, data, isHidden)

    }

    fun runPeriodicTask() {

        mDataComputationScope.launch {
            while (mDataComputationScope.isActive) {
                // TODO should I periodic shift

                /*   if(sensorType == Sensor.TYPE_GYROSCOPE){
                       Log.d("MpChartViewManager ", "runPeriodicTask : $sensorType")
                   }*/
                var items = addPreEntry()

//                Log.d("ChartDataHandler", "¿Is runPeriodicTask?: true" )
//                Log.d("ChartDataHandler", "Data: $sensorType | ${items.size} | $items" )
                _mSensorPacketFlow.emit(
                    ModelChartUiUpdate(
//                    mModelLineChart
                        sensorType,
                        items.size,
                        items,
                    )
                )

                delay(SensorsConstants.MAP_DELAY_TYPE_TO_DELAY.get(mUIRefreshDelay).toLong())

            }

        }
        mDataAddScope.launch {
            while (mDataAddScope.isActive) {
                // TODO should I periodic shift

                synchronized(mLockDataAdd) {
                    if (mCurrentPacket != null) {
                        mPre.add(mCurrentPacket!!)
                    }
                }

                delay(SensorsConstants.MAP_DELAY_TYPE_TO_DELAY.get(mDataRefreshDelay).toLong())

            }

        }
    }


    fun addEntry(sensorPacket: ModelSensorPacket) {

        synchronized(mLockDataAdd) {

            mCurrentPacket = sensorPacket.copy()

            // TODO do in periodic place mPre.add(sensorPacket)

        }


    }

    private fun addPreEntry(): MutableList<ModelSensorPacket> {
        var preData: MutableList<ModelSensorPacket>
        synchronized(mLockDataAdd) {
            preData = mPre
            mPre = mutableListOf()
        }

        val needToChangeUi = preData.size > 0

        for (item in preData) {

            for (index in mDataTypesIndexed.indices) {

                if (item.values != null) {
                    if (item.values!!.size > index) {
                        mModelLineChart.addEntry(mDataTypesIndexed[index], item.values!![index]);
                    }
                }
                //loops all indices (performs just as well as two examples above)
            }
        }

        return preData;

    }


    /*private fun shiftData(set: ILineDataSet) {
        if (set.entryCount > mModelLineChart!!.getSampleLength()) {
            set.removeEntry(0) // remove oldest
            // change Indexes - move to beginning by 1
            for (i in 1 until set.entryCount) {
                val entry = set.getEntryForIndex(i)
                entry.x = entry.x - 1
            }
        }
    }*/


}



