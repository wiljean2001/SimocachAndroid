package com.io.simocach_android.ui.pages.home.items

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.io.simocach_android.domains.chart.entity.ModelChartUiUpdate
import com.io.simocach_android.domains.chart.mpchart.MpChartDataManager
import com.io.simocach_android.domains.chart.mpchart.MpChartViewBinder
import com.io.simocach_android.domains.chart.mpchart.MpChartViewUpdater
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.ui.components.chart.mpchart.base.MpChartLineView
import com.io.simocach_android.ui.pages.home.model.ModelHomeSensor
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.Shapes
import com.io.simocach_android.ui.resources.values.TxtStyles

@Composable
fun HomeSensorChartItem(
    modelSensor: ModelHomeSensor = ModelHomeSensor(
        type = MySensor.TYPE_TURBIDITY
    ),
    mpChartDataManager : MpChartDataManager = MpChartDataManager(modelSensor.type),
    mpChartViewUpdater: MpChartViewUpdater = MpChartViewUpdater(),

    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    val state = mpChartDataManager.mSensorPacketFlow.collectAsState(
        initial = ModelChartUiUpdate(
            sensorType = modelSensor.type,
            0,
            listOf()
        )
    )
    val sensorUiUpdate = remember {
        state
    };

    Log.d("HomeSensorChart", "Chart model: ${modelSensor.name} ${modelSensor.type}  ${mpChartDataManager.sensorType}")

    var colorSurface = MaterialTheme.colorScheme.surface
    val colorOnSurface = MaterialTheme.colorScheme.onSurface
    Column(
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(horizontal = Dimens.dp12, vertical = Dimens.dp12)
//            .height(Dimens.dp168)
            .fillMaxSize(),
    ) {

        Text(
            modifier = Modifier.padding(horizontal = Dimens.dp12),
            text = modelSensor.name,

            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            style = TxtStyles.h5,
        )
        AndroidView(
            modifier = Modifier
                .background(color = Color.Transparent)
//            .height(Dimens.dp168)
                .fillMaxSize(),


            factory = { ctx ->

                Log.v("HomeSensorChart", "factory: ${mpChartDataManager.sensorType}")

                val view = MpChartLineView(modelSensor.type);
//                view
                return@AndroidView MpChartViewBinder(
                    ctx,
                    view,
                    colorOnSurface = colorOnSurface
                ).prepareDataSets(mpChartDataManager.getModel())
                    .invalidate()
//                mpChartViewManager.createChart(ctx, colorSurface, colorOnSurface)
            }
        ) {
//                Log.v("HomeSensorChart", "update:${sensorUiUpdate.value.sensorType} ${it.tag}  ${sensorUiUpdate.value.timestamp} ${sensorUiUpdate.value.size}")

            mpChartViewUpdater.update(it, sensorUiUpdate.value, mpChartDataManager.getModel())
//                Log.v("HomeSensorChart", "update: ${mpChartDataManager.sensorType} ${isUpdated}")

//                mpChartDataManager.runPeriodically()
            //updateData(it, sensorUiUpdate.value)
        }
        Spacer(modifier = Shapes.Space.H18)

    }

    DisposableEffect(mpChartDataManager.sensorType) {
        /*  val observer = LifecycleEventObserver { _, event ->
              if (event == Lifecycle.Event.ON_START) {
  //                currentOnStart()
              } else if (event == Lifecycle.Event.ON_DESTROY) {
  //                currentOnStop()
                  Log.v("HomeSensorChart", "destroy: ${mpChartDataManager.sensorType}")
                  mpChartDataManager.destroy()
              }
          }

          // Add the observer to the lifecycle
          lifecycleOwner.lifecycle.addObserver(observer)*/
        onDispose {

            Log.v("HomeSensorChart", "dispose: ${mpChartDataManager.sensorType}")
//            mpChartDataManager.destroy()
//            lifecycleOwner.lifecycle.removeObserver(observer)


        }
    }
}