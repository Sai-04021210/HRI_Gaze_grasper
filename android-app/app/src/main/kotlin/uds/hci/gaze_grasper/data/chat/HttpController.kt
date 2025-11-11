package uds.hci.gaze_grasper.data.chat

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uds.hci.gaze_grasper.domain.chat.BluetoothController
import uds.hci.gaze_grasper.domain.chat.BluetoothDeviceDomain
import uds.hci.gaze_grasper.domain.chat.BluetoothMessage
import uds.hci.gaze_grasper.domain.chat.ConnectionResult
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class HttpController : BluetoothController {
    override val isConnected: Flow<Boolean> = flow { emit(true) }
    override val scannedDevices: Flow<List<BluetoothDeviceDomain>> = flow { emit(emptyList()) }
    override val pairedDevices: Flow<List<BluetoothDeviceDomain>> = flow { emit(emptyList()) }
    override val errors: Flow<String> = flow { emit("") }

    override fun startDiscovery() {}
    override fun stopDiscovery() {}

    override fun startBluetoothServer(): Flow<ConnectionResult> {
        return flow { emit(ConnectionResult.ConnectionEstablished) }
    }

    override fun connectToDevice(device: BluetoothDeviceDomain): Flow<ConnectionResult> {
        return flow { emit(ConnectionResult.ConnectionEstablished) }
    }

    override suspend fun sendMessage(message: String): BluetoothMessage? {
        // For simplicity, we'll assume the message is the object name
        return selectObject(message)
    }

    suspend fun selectObject(objectName: String): BluetoothMessage? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://192.168.0.61:5001/arm/move") // IP address of the computer running the robot arm controller
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val json = "{\"object\": \"$objectName\"}"

                val writer = OutputStreamWriter(connection.outputStream)
                writer.write(json)
                writer.flush()

                connection.disconnect()

                BluetoothMessage(objectName, "Android App", true)
            } catch (e: Exception) {
                Log.e("HttpController", "Error sending message: ${e.message}")
                null
            }
        }
    }

    override fun sendRawData(bytes: ByteArray) {
        // HTTP-based implementation for sending raw data
        // Must run on IO thread since we're doing network operations
        kotlinx.coroutines.CoroutineScope(Dispatchers.IO).launch {
            try {
                val blockId = bytes.firstOrNull()?.toInt() ?: 0
                Log.i("HttpController", "Sending block_id: $blockId to robot arm controller")

                val url = URL("http://192.168.0.61:5001/arm/move")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val json = "{\"block_id\": $blockId}"
                Log.d("HttpController", "Sending JSON: $json")

                val writer = OutputStreamWriter(connection.outputStream)
                writer.write(json)
                writer.flush()

                val responseCode = connection.responseCode
                Log.i("HttpController", "Response code: $responseCode")

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("HttpController", "Error sending raw data: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    override fun closeConnection() {}
    override fun release() {}
}
