package uds.hci.gaze_grasper.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uds.hci.gaze_grasper.data.chat.HttpController
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val httpController: HttpController
) : ViewModel() {

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
}

data class MainUiState(
    val isConnected: Boolean = true,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null
)
