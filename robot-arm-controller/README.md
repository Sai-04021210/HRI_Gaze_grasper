# Gaze-Grasper Robot Arm Controller

This is the controlling instance for the simulated Gaze-Grasper wearable robotic arm. It is a Python-based web server that receives commands from the Android app to control the arm's movements in the virtual environment.

## Getting Started

To get started with the robot arm controller, you will need to install the necessary Python dependencies and then run the web server.

### Prerequisites

*   Python 3.11 or later
*   pip

### Installation

1.  Navigate to this directory in your terminal.
2.  Install the required dependencies using pip:

    ```bash
    pip install -r requirements.txt
    ```

    The required dependencies include:
    - Flask - Web framework for the server
    - Flask-Cors - Cross-Origin Resource Sharing support
    - dynamixel_sdk - SDK for controlling Dynamixel servos

### Running the Web Server

Once the dependencies are installed, you can start the web server by running the following command:

```bash
python3 server.py
```

**Note:** If you encounter module import errors, ensure you're using the same Python version that pip installed packages to. You may need to specify the full path to your Python 3.11 executable:

```bash
/Library/Frameworks/Python.framework/Versions/3.11/bin/python3 server.py
```

The server will start on `http://127.0.0.1:5001` in debug mode.
