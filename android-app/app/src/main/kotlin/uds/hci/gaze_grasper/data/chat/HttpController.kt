package uds.hci.gaze_grasper.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import uds.hci.gaze_grasper.domain.chat.BluetoothController
import uds.hci.gaze_grasper.domain.chat.BluetoothDevice
import uds.hci.gaze_grasper.domain.chat.BluetoothDeviceDomain
import uds.hci.gaze_grasper.domain.chat.BluetoothMessage
import uds.hci.gaze_grasper.domain.chat.ConnectionResult
import uds.hci.gaze_grasper.domain.gaze.RawDataSender
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class HttpController : BluetoothController, RawDataSender {
    override val isConnected: StateFlow<Boolean> = MutableStateFlow(true)
    override val scannedDevices: StateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList())
    override val errors: SharedFlow<String> = MutableSharedFlow()

    override fun startDiscovery() {}
    override fun stopDiscovery() {}

    override fun startBluetoothServer(): Flow<ConnectionResult> {
        return flow { emit(ConnectionResult.ConnectionEstablished) }
    }

    override fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        return flow { emit(ConnectionResult.ConnectionEstablished) }
    }

    override suspend fun sendMessage(message: String): BluetoothMessage? {
        val url = URL("http://10.0.2.2:5001/arm/move") // 10.0.2.2 is the localhost address for the Android emulator
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val json = "{\"message\": \"$message\"}"

        val writer = OutputStreamWriter(connection.outputStream)
        writer.write(json)
        writer.flush()

        connection.disconnect()

        return BluetoothMessage(message, "Android App", true)
    }

    override fun sendRawData(bytes: ByteArray) {
        // HTTP-based implementation for sending raw data
        try {
            val url = URL("http://10.0.2.2:5001/arm/move")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            // Convert byte array to JSON message
            val blockId = bytes.firstOrNull()?.toInt() ?: 0
            val json = "{\"block_id\": $blockId}"

            val writer = OutputStreamWriter(connection.outputStream)
            writer.write(json)
            writer.flush()

            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun closeConnection() {}
    override fun release() {}
}
