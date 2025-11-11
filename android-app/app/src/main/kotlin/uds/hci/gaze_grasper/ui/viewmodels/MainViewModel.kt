package uds.hci.gaze_grasper.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uds.hci.gaze_grasper.domain.chat.BluetoothController
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val httpController: BluetoothController
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state = _state.asStateFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            httpController.sendMessage(message)
        }
    }
}

data class MainUiState(
    val isConnected: Boolean = true,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null
)
