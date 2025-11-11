package uds.hci.gaze_grasper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import uds.hci.gaze_grasper.domain.gaze.BlocksManager
import uds.hci.gaze_grasper.domain.gaze.GazeTrackerManager
import uds.hci.gaze_grasper.dto.gaze.PixyBlock
import uds.hci.gaze_grasper.ui.components.GazeTrackingScreen
import uds.hci.gaze_grasper.ui.components.PermissionsHandler
import uds.hci.gaze_grasper.ui.theme.GazeGrasperTheme
import uds.hci.gaze_grasper.ui.viewmodels.MainViewModel

/**
 * Main Class where the UI aspects and general information were handled.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set app to fullscreen (landscape orientation set via manifest)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        val screenWidth = windowManager.currentWindowMetrics.bounds.width()
        val screenHeight = windowManager.currentWindowMetrics.bounds.height()

        val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        setContent {
            GazeGrasperTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val state by viewModel.state.collectAsState()

                val blocksManager = BlocksManager(
                    screenWidth to screenHeight,
                    toast,
                    viewModel
                )
                val gazeTrackerManager = GazeTrackerManager(applicationContext, blocksManager)

                // TODO: remove mock-PixyBlock once they get received over the network
                blocksManager.addBlocks(
                    listOf(
                        PixyBlock(0, 266, 20, 100, 50, 0, 0, 0)
                    )
                )

                GazeTrackingScreen(gazeTrackerManager = gazeTrackerManager, blocksManager = blocksManager)
                PermissionsHandler(::shouldShowRequestPermissionRationale)
            }
        }
    }
}
