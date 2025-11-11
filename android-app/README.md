# Gaze-Grasper Android App

This is the Android app for the Gaze-Grasper project. It is a wearable robot which is able to reach for and interact with various objects just by tracking the users gaze. We also included the feature to use facial expressions. This app sends commands to the robot arm controller to control the simulated robot arm.

## Development Setup

### Option 1: Android Studio (Recommended)

The project requires Android Studio 2022.3 (Giraffe) or later. It should automatically create an `app` run configuration when opened. This builds the application and deploys it on your default Android virtual device.

### Option 2: Command Line Build (macOS)

If you prefer to build and deploy from the command line without Android Studio, follow these steps:

#### Prerequisites
- macOS (tested on macOS Sequoia)
- Homebrew package manager
- Android device with USB debugging enabled

#### Step 1: Install Java 17

```bash
# Install OpenJDK 17 via Homebrew
brew install openjdk@17

# Set up Java environment (add to ~/.zshrc for persistence)
export JAVA_HOME="/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

# Verify installation
java -version
```

#### Step 2: Install Android Command Line Tools

```bash
# Install Android SDK command-line tools
brew install --cask android-commandlinetools

# Set up Android environment (add to ~/.zshrc for persistence)
export ANDROID_HOME="/opt/homebrew/share/android-commandlinetools"

# Accept SDK licenses
sdkmanager --licenses

# Install required SDK components
sdkmanager "platforms;android-34" "build-tools;34.0.0" "platform-tools"
```

#### Step 3: Configure Project

Create a `local.properties` file in the APP directory:

```bash
echo "sdk.dir=/opt/homebrew/share/android-commandlinetools" > local.properties
```

#### Step 4: Make Gradle Wrapper Executable

```bash
chmod +x gradlew
```

#### Step 5: Connect Android Device

1. Enable Developer Options on your Android device:
   - Go to Settings > About Phone
   - Tap "Build Number" 7 times

2. Enable USB Debugging:
   - Go to Settings > Developer Options
   - Enable "USB Debugging"

3. Connect your device via USB and verify:

```bash
adb devices
```

4. Authorize the connection on your device when prompted

#### Step 6: Build and Install

```bash
# Build the debug APK
./gradlew assembleDebug

# Install on connected device
adb install app/build/outputs/apk/debug/app-debug.apk
```

#### Alternative: Build and Install in One Step

```bash
./gradlew installDebug
```

in the project root.

## Communication

This app communicates with the robot arm controller via HTTP requests. The app sends the user's gaze and facial expression data to the controller, which then updates the state of the simulated robot arm.
### Environment Setup for Persistence

To avoid setting environment variables every time, add these lines to your `~/.zshrc`:

```bash
# Java
export JAVA_HOME="/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"

# Android SDK
export ANDROID_HOME="/opt/homebrew/share/android-commandlinetools"
export PATH="$ANDROID_HOME/platform-tools:$PATH"
```

Then reload your shell:

```bash
source ~/.zshrc
```

## Running the App

After installation, the "Gaze Grasper" app will appear in your Android device's app drawer. Launch it to:
- Track your gaze using the front camera
- Detect facial expressions
- Send control data to the robotic arm (requires Python controller running)

## Troubleshooting

- **"SDK location not found"**: Make sure `local.properties` exists with the correct `sdk.dir` path
- **"Unable to locate a Java Runtime"**: Verify `JAVA_HOME` is set correctly
- **"Device unauthorized"**: Check your phone for the USB debugging authorization prompt
- **Build fails with dependency errors**: Run `./gradlew clean` and try again
