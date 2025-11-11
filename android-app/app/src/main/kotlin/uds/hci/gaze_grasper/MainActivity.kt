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
import uds.hci.gaze_grasper.di.AppModule
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

                // 5 blocks: 3 pick targets (top) and 2 drop locations (bottom)
                // PixyBlock(signature, x, y, width, height, angle, index, age)
                // Pixy coordinates: x: 0-316, y: 0-208
                // Smaller blocks (40x28 - half of previous 80x55)
                blocksManager.addBlocks(
                    listOf(
                        // Pick targets (top row)
                        PixyBlock(0, 60, 50, 40, 28, 0, 0, 0),    // Apple (Red - index 0)
                        PixyBlock(0, 160, 50, 40, 28, 0, 1, 0),   // Pen (Teal - index 1)
                        PixyBlock(0, 260, 50, 40, 28, 0, 2, 0),   // Lego (Orange - index 2)
                        // Drop locations (bottom row - closer to top row)
                        PixyBlock(0, 110, 100, 40, 28, 0, 3, 0),  // Drop-1 (Purple - index 3)
                        PixyBlock(0, 210, 100, 40, 28, 0, 4, 0)   // Drop-2 (Green - index 4)
                    )
                )

                GazeTrackingScreen(gazeTrackerManager = gazeTrackerManager, blocksManager = blocksManager)
                PermissionsHandler(::shouldShowRequestPermissionRationale)
            }
        }
    }
}
