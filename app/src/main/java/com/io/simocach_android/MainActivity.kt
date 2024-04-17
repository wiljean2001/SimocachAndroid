package com.io.simocach_android

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.SensorData
import com.io.simocach_android.domains.sensors.data.WebSocketClient
import com.io.simocach_android.domains.sensors.packets.SensorPacketsProvider
import com.io.simocach_android.domains.sensors.provider.SensorsProvider
import com.io.simocach_android.ui.navigation.NavGraphApp
import com.io.simocach_android.ui.resources.theme.SimocachAndroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

//    private val webSocketClient = WebSocketClient("ws://192.168.4.1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            // Inicia la conexión WebSocket
            WebSocketClient.getInstance().connect()

            setContent {
                SimocachAndroidTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraphApp();
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        scope
//        coroutineScope {
        SensorsProvider.getInstance().clearAll();


        SensorPacketsProvider.getInstance().clearAll();
//        }
        lifecycleScope.launch {

        }
        CoroutineScope(Job())

        SensorData.getInstance().stopSimulatedSensorUpdates()

        WebSocketClient.getInstance().disconnect()

        //setSensorManager(sensorManager)
    }

    suspend fun dsds() {
        suspendCoroutine<Unit> { }
    }
}