package uds.hci.gaze_grasper.domain.gaze

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import uds.hci.gaze_grasper.dto.gaze.DisplayablePixyBlock
import uds.hci.gaze_grasper.dto.gaze.GazeCoordinates
import uds.hci.gaze_grasper.dto.gaze.PixyBlock
import uds.hci.gaze_grasper.ui.viewmodels.MainViewModel

class BlocksManager(
    private val resolution: Pair<Int, Int>,
    private val toast: Toast,
    private val viewModel: MainViewModel
) {
    val blocks = mutableStateListOf<DisplayablePixyBlock>()
    var gazedBlockId = -1
        private set
    private var heldObject: String? = null

    fun addBlocks(pixyBlocks: List<PixyBlock>) {
        blocks.clear()
        pixyBlocks.forEach { pixyBlock ->
            blocks.add(DisplayablePixyBlock(pixyBlock, resolution))
        }
    }

    fun onGaze(gazeCoords: GazeCoordinates) {
        gazedBlockId = -1
        blocks.forEach { block ->
            block.onGaze(gazeCoords)
            if (block.isGazeWithin) {
                gazedBlockId = block.id
            }
        }
    }

    fun onBlockSelection(id: Int) {
        if (id == -1) {
            return
        }

        if (heldObject == null) {
            val objectName = getObjectName(id)
            if (objectName != null) {
                Log.i("BlocksManager", "PixyBlock id:$id selected!")
                toast.setText("Pixy Block id:$id selected!")
                toast.show()
                viewModel.selectObject(objectName)
                heldObject = objectName
            }
        } else {
            val dropZoneName = getDropZoneName(id)
            if (dropZoneName != null) {
                Log.i("BlocksManager", "Drop zone id:$id selected!")
                toast.setText("Drop zone id:$id selected!")
                toast.show()
                viewModel.placeObject(dropZoneName)
                heldObject = null
            }
        }
    }

    private fun getObjectName(id: Int): String? {
        // This is a simplified mapping. In a real application,
        // you would have a more robust way of identifying objects.
        return when (id) {
            0 -> "apple"
            1 -> "pen"
            2 -> "lego"
            else -> null
        }
    }

    private fun getDropZoneName(id: Int): String? {
        // This is a simplified mapping. In a real application,
        // you would have a more robust way of identifying drop zones.
        return when (id) {
            3 -> "drop1"
            4 -> "drop2"
            else -> null
        }
    }
}
