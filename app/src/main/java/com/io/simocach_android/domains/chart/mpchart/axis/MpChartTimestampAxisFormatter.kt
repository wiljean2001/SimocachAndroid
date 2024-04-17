package com.io.simocach_android.domains.chart.mpchart.axis

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.io.simocach_android.domains.sensors.SensorsConstants.MAP_DELAY_TYPE_TO_DELAY

class MpChartTimestampAxisFormatter(var sensorDelay: Int = 3) : ValueFormatter() {

    fun setDelay(delay: Int){
        sensorDelay = delay;
    }
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {

        /* val LENGTH_SAMPLE: Int = mModelLineChart.getSampleLength()
         val labels = arrayOfNulls<String>(LENGTH_SAMPLE)
         val timePreiod: Float = mModelLineChart.getTimePeriod()
         for (i in labels.indices) {
             val `val` = i.toFloat() * timePreiod
             labels[i] = java.lang.Float.toString(`val`) + "s"
         }*/
        var delay  = MAP_DELAY_TYPE_TO_DELAY.get(sensorDelay)
        var totalDelay = value*delay;
        return  "${totalDelay/1000}s"
//        return super.getAxisLabel(value, axis)
    }

}