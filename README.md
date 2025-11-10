# Gaze-Grasper

This project is a wearable robotic arm that can be controlled by the user's gaze and facial expressions. It allows users to reach for, grasp, release, and manipulate objects in their environment.

## Components

The Gaze-Grasper project is divided into three main components:

1.  **Android App (`./android-app/`)**: This is the main user interface for the project. It runs on an Android device and is responsible for tracking the user's gaze and facial expressions. This information is then used to control the robotic arm. For more information, see the [Android App README](./android-app/README.md).

2.  **Robot Arm Controller (`./robot-arm-controller/`)**: This component is the controlling instance for the wearable robotic arm. It is written in Python and receives commands from the Android app to control the arm's movements. For more details, refer to the [Robot Arm Controller README](./robot-arm-controller/README.md).

3.  **Leaderboard (`./leaderboard-web-app/`)**: A web-based application that displays a leaderboard. This is likely used for tracking performance or for gamification purposes. For more information, see the [Leaderboard README](./leaderboard-web-app/README.md).

## Getting Started

To get started with the Gaze-Grasper project, you will need to set up each of the components individually. Please refer to the README file in each component's directory for specific setup instructions.
