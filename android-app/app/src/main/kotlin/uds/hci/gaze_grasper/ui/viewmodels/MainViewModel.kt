package uds.hci.gaze_grasper.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uds.hci.gaze_grasper.domain.chat.BluetoothController
import uds.hci.gaze_grasper.domain.gaze.RawDataSender
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val httpController: BluetoothController
) : ViewModel(), RawDataSender {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    fun selectObject(objectName: String) {
        viewModelScope.launch {
            httpController.selectObject(objectName)
        }
    }

    fun placeObject(dropZoneName: String) {
        viewModelScope.launch {
            httpController.placeObject(dropZoneName)
        }
    }

    override fun sendRawData(bytes: ByteArray) {
        if (httpController is RawDataSender) {
            httpController.sendRawData(bytes)
        }
    }
}

data class MainUiState(
    val isConnected: Boolean = true,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null
)
