# Gaze-Grasper Robot Arm Controller

This is the controlling instance for the simulated Gaze-Grasper wearable robotic arm. It is a Python-based web server that receives commands from the Android app to control the arm's movements in the virtual environment.

## Getting Started

To get started with the robot arm controller, you will need to install the necessary Python dependencies and then run the web server.

### Prerequisites

*   Python 3.11 or later
*   pip

### Installation

1.  Navigate to this directory in your terminal.
2.  Create a virtual environment (recommended):

    ```bash
    python3 -m venv venv
    source venv/bin/activate  # On Windows: venv\Scripts\activate
    ```

3.  Install the required dependencies using pip:

    ```bash
    pip install -r requirements.txt
    ```

    The required dependencies include:
    - Flask - Web framework for the server
    - Flask-Cors - Cross-Origin Resource Sharing support
    - dynamixel_sdk - SDK for controlling Dynamixel servos
    - numpy - For numerical computations in inverse kinematics

### Running the Web Server

Once the dependencies are installed, you can start the web server by running the following command:

```bash
source venv/bin/activate  # Activate virtual environment
python3 server.py
```

**Note:** If you encounter module import errors, ensure you're using the same Python version that pip installed packages to. You may need to specify the full path to your Python 3.11 executable:

```bash
/Library/Frameworks/Python.framework/Versions/3.11/bin/python3 server.py
```

The server will start on `http://0.0.0.0:5001` (accessible on all network interfaces) in debug mode.

## API Endpoints

### GET `/arm/state`
Returns the current state of the virtual arm including motor positions, speeds, and sensor data.

**Response:**
```json
{
  "motor_positions": [0, 0, 0],
  "motor_speeds": [0, 0, 0],
  "ultrasonic_distance": 0,
  "object_in_view": false,
  "object_centered": false,
  "object_in_quarter_frame": false
}
```

### POST `/arm/move`
Moves the virtual arm to a specified position.

**Request:**
```json
{
  "block_id": 0  // 0-4 for predefined positions
}
```

**Predefined Positions:**
- **Block 0 (Apple)**: Right side pick - `[30.0, 15.0, 0.0]`
- **Block 1 (Pen)**: Center pick (low) - `[0.0, 25.0, 0.0]`
- **Block 2 (Lego)**: Left side pick - `[-30.0, 15.0, 0.0]`
- **Block 3 (Drop-1)**: Center drop zone - `[0.0, 35.0, 0.0]`
- **Block 4 (Drop-2)**: Right drop zone - `[20.0, 25.0, 0.0]`

**Response:**
```json
{
  "status": "success",
  "position": [30.0, 15.0, 0.0]
}
```

## Network Configuration

The server listens on `0.0.0.0:5001`, making it accessible from:
- Localhost: `http://127.0.0.1:5001`
- Local network: `http://192.168.0.61:5001` (replace with your computer's IP)

**Important:** Ensure your Android device and computer are on the same Wi-Fi network for communication.
