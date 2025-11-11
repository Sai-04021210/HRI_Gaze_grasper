package uds.hci.gaze_grasper.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun closeConnection() {}
    override fun release() {}
}
