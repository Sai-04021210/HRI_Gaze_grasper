# Gaze-Grasper Android App

This is the Android app for the Gaze-Grasper project. It is a wearable robot which is able to reach for and interact with various objects just by tracking the users gaze. We also included the feature to use facial expressions. This app sends commands to the robot arm controller to control the simulated robot arm.

## Development setup

The project requires Android Studio 2022.3 (Giraffe) or later. It should automatically create an `app` run configuration when opened. This builds the application and deploys it on your default Android virtual device.

In order to build the project from the command line (e.g. for debugging purposes), make sure your `JAVA_HOME` system environment variable is set (required Java version >= 17) and initialize the Gradle CLI by running

```bash
./gradlew wrapper --gradle-version 8.3
```

in the project root.

## Communication

This app communicates with the robot arm controller via HTTP requests. The app sends the user's gaze and facial expression data to the controller, which then updates the state of the simulated robot arm.
