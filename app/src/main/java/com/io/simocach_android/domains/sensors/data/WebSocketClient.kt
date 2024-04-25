package com.io.simocach_android.domains.sensors.data

import android.content.Context
import android.util.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
class WebSocketClient(private val serverUrl: String) {

    companion object {
        private var sWebSocketClient: WebSocketClient? = null
        private val lock = Any()
        private lateinit var context: Context
        fun init(context: Context) {
            this.context = context
        }

        fun getInstance(): WebSocketClient {
            synchronized(lock) {
                if (sWebSocketClient == null) {
                    val prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val websocketIp = prefs.getString("websocket_ip", "192.168.4.1") ?: "192.168.4.1"
                    sWebSocketClient = WebSocketClient(websocketIp)
                }
                return sWebSocketClient!!
            }
        }
    }

    private val client = HttpClient(CIO) {
        install(WebSockets){
            pingInterval = 20_000
        }
    }
    private val json = Json { ignoreUnknownKeys = true }

    private var latestSensorData: SensorDataSerializer? = null

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun connect() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = serverUrl,
                    port = 80,
                    path = "ws"
                ) {
                    // Handle connection established
                    launch { send("Hello, server!") }

                    // Listen for incoming messages
                    for (frame in incoming) {
                        if (frame is Frame.Text) {
                            val message = frame.readText()
                            Log.d("WebSocketClient", "$message")
                            handleWebSocketMessage(message)
                        }
                    }
                }
                Log.e("WebSocketClient", "Connected")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("WebSocketClient", "Error: ${e.message}")
            }
        }
    }

    fun disconnect() {
        client.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun handleWebSocketMessage(message: String) {
        try {
            val sensorData = json.decodeFromString<SensorDataSerializer>(message)
            // Aquí puedes procesar los datos del sensor recibidos
            println("Temperature: ${sensorData.temperature}")
            println("Turbidity: ${sensorData.turbidity}")
            println("TDS: ${sensorData.tds}")
            println("pH: ${sensorData.ph}")
            latestSensorData = sensorData
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Método para obtener los datos más recientes
    fun getLatestSensorData(): SensorDataSerializer? {
        return latestSensorData
    }

}
