package com.io.simocach_android.ui.pages.sensor.sections

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.io.simocach_android.domains.chart.entity.ModelChartUiUpdate
import com.io.simocach_android.domains.chart.mpchart.MpChartDataManager
import com.io.simocach_android.domains.chart.mpchart.MpChartViewBinder
import com.io.simocach_android.domains.chart.mpchart.MpChartViewUpdater
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.provider.ModelSensor
import com.io.simocach_android.ui.components.chart.mpchart.MpChartLineAxis
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.Shapes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun  SensorChart(
    modelSensor: ModelSensor = ModelSensor(
        type = MySensor.TYPE_TEMPERATURE
    ),
    mpChartDataManager : MpChartDataManager = MpChartDataManager(modelSensor.type),
    mpChartViewUpdater: MpChartViewUpdater = MpChartViewUpdater(),
    sensorPacketFlow: SharedFlow<ModelChartUiUpdate> = MutableSharedFlow<ModelChartUiUpdate>(replay = 0),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
){
    Log.d("HomeSensorChart", "Chart model: ${modelSensor.name} ${modelSensor.type}  ${mpChartDataManager.sensorType}")

    //    var mpChartViewManager = MpChartViewManager(modelSensor.type)
//    val sensorUiUpdate = rememberChartUiUpdateEvent(mpChartDataManager, SensorManager.SENSOR_DELAY_NORMAL)
    val state =sensorPacketFlow.collectAsState(
        initial = ModelChartUiUpdate(
            sensorType = modelSensor.type,
            0,
            listOf()
        )
    )
    val sensorUiUpdate = remember {
        state
    };

//    var counter = 0
//    Log.d("DefaultChartTesting", "Linechart isUpdating ${isUpdating.value}")
    var colorSurface = MaterialTheme.colorScheme.surface
    var colorOnSurface = MaterialTheme.colorScheme.onSurface
    Column(
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(horizontal = Dimens.dp12, vertical = Dimens.dp12)
//            .height(Dimens.dp168)
            .fillMaxSize(),
    ) {

        /*   Text(
               modifier = Modifier.padding(horizontal = Dimens.dp12),
               text = "${modelSensor.name}",


               color = MaterialTheme.colorScheme.onSurface,
               textAlign = TextAlign.Start,
               style = JlResTxtStyles.h5,
           )*/

        AndroidView(
            modifier = Modifier
                .background(color = Color.Transparent)
//            .height(Dimens.dp168)
                .fillMaxSize(),


            factory = { ctx ->

                Log.v("HomeSensorChart", "factory: ${mpChartDataManager.sensorType}")

                var view = MpChartLineAxis(modelSensor.type);
//                view
                val lineChart = MpChartViewBinder(ctx, view, colorOnSurface= colorOnSurface).prepareDataSets(mpChartDataManager.getModel())
                    .invalidate()
                return@AndroidView lineChart
//                mpChartViewManager.createChart(ctx, colorSurface, colorOnSurface)
            },
            update = {
//                Log.v("HomeSensorChart", "update aa: ${sensorUiUpdate.value.sensorType} ${it.tag}  ${sensorUiUpdate.value.timestamp} ${sensorUiUpdate.value.size}")

                mpChartViewUpdater.update(it, sensorUiUpdate.value, mpChartDataManager.getModel())
//                Log.v("HomeSensorChart", "update: ${mpChartDataManager.sensorType} ${isUpdated}")

//                mpChartDataManager.runPeriodically()
                //updateData(it, sensorUiUpdate.value)
            }
        )
        Spacer(modifier = Shapes.Space.H18)

    }


}