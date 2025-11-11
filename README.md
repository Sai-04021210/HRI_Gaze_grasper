# Gaze-Grasper

This project is a simulation of a wearable robotic arm that can be controlled by a user's gaze and facial expressions, or by clicking on objects in the web-based visualization. It allows users to reach for, grasp, release, and manipulate objects in a virtual environment.

## Components

The Gaze-Grasper project is divided into three main components:

1.  **Android App (`./android-app/`)**: The main user interface for the project. It runs on an Android device and is responsible for tracking the user's gaze and facial expressions. This information is then used to control the simulated robotic arm. For more information, see the [Android App README](./android-app/README.md).

2.  **Robot Arm Controller (`./robot-arm-controller/`)**: This component is the controlling instance for the simulated wearable robotic arm. It is written in Python and runs a web server that receives commands from the Android app or the web-based controller to control the arm's movements. For more details, refer to the [Robot Arm Controller README](./robot-arm-controller/README.md).

3.  **Visualization Frontend (`./visualization-frontend/`)**: A web-based application that provides a 2D visualization of the simulated robot arm and its environment. It communicates with the robot arm controller to display the arm's movements in real-time. It also includes a web-based controller that allows you to control the arm by clicking on objects and drop zones. For more information, see the [Visualization Frontend README](./visualization-frontend/README.md).

## Getting Started

To get started with the Gaze-Grasper project, you will need to set up each of the components individually. Please refer to the README file in each component's directory for specific setup instructions.
