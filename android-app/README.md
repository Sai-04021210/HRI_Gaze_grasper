# Gaze-Grasper Android App

This is the Android app for the Gaze-Grasper project. It is a wearable robot which is able to reach for and interact with various objects just by tracking the users gaze. We also included the feature to use facial expressions. This app sends commands to the robot arm controller to control the simulated robot arm.

## Requirements

- Android device with API level 31 or higher
- Camera permission for gaze tracking
- Device and computer must be on the same Wi-Fi network

## Development Setup

### Option 1: Using Android Studio

The project requires Android Studio 2022.3 (Giraffe) or later. It should automatically create an `app` run configuration when opened. This builds the application and deploys it on your default Android virtual device.

### Option 2: Using Command Line with ADB

1. **Install Java JDK 17 or higher**:
   ```bash
   # On macOS using Homebrew
   brew install openjdk@17
   ```

2. **Set JAVA_HOME environment variable**:
   ```bash
   export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
   ```

3. **Build the APK**:
   ```bash
   chmod +x ./gradlew
   ./gradlew assembleDebug
   ```

4. **Connect your Android device** via USB and enable USB debugging

5. **Install the APK using ADB**:
   ```bash
   adb devices  # Verify device is connected
   adb install -r app/build/outputs/apk/debug/app-debug.apk
   ```

## Configuration

### Server IP Address

The app communicates with the robot arm controller via HTTP. You need to configure the server IP address to match your computer's local network IP.

1. **Find your computer's IP address**:
   ```bash
   ifconfig | grep "inet " | grep -v 127.0.0.1
   ```

2. **Update the IP in the code**:
   Edit `app/src/main/kotlin/uds/hci/gaze_grasper/data/chat/HttpController.kt` and replace the IP address in both URL locations:
   ```kotlin
   val url = URL("http://YOUR_COMPUTER_IP:5001/arm/move")
   ```

3. **Rebuild and reinstall** the app after changing the IP address

### SeeSo Gaze Tracker License

The app uses the SeeSo Gaze Tracker SDK which requires a valid license key.

1. **Get a license key** from [SeeSo.io](https://seeso.io/)
2. **Update the license key** in `app/src/main/kotlin/uds/hci/gaze_grasper/domain/gaze/GazeTrackerManager.kt`:
   ```kotlin
   private const val LICENSE_KEY = "your_license_key_here"
   ```

## Communication

This app communicates with the robot arm controller via HTTP requests. The app sends the user's gaze and facial expression data to the controller, which then updates the state of the simulated robot arm.

- **Default Port**: 5001
- **Endpoint**: `/arm/move`
- **Method**: POST
- **Content-Type**: application/json

## Usage

1. **Launch the app** on your Android device
2. **Grant camera permission** when prompted
3. **Click "Start gaze tracking"** button
4. **Complete calibration**: Look at each red dot with circular progress indicator as it appears at different positions on screen
5. **Start tracking**: After calibration, a small red dot will follow your gaze
6. **Select objects**: Blink 3 times quickly while looking at a cyan box to select it and send the command to the robot arm controller
