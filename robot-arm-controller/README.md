# Gaze-Grasper Robot Arm Controller

This is the controlling instance for the simulated Gaze-Grasper wearable robotic arm. It is a Python-based web server that receives commands from the Android app or the web-based controller to control the arm's movements in the virtual environment.

## Getting Started

To get started with the robot arm controller, you will need to install the necessary Python dependencies and then run the web server.

### Prerequisites

*   Python 3.6 or later
*   pip

### Installation

1.  Navigate to this directory in your terminal.
2.  Install the required dependencies using pip:

    ```bash
    pip install -r requirements.txt
    ```

### Running the Web Server

Once the dependencies are installed, you can start the web server by running the following command:

```bash
python server.py
```

The server will start on `http://localhost:5001`.

## API Endpoints

The web server exposes the following API endpoints:

*   `GET /arm/state`: Returns the current state of the virtual arm, including the motor positions, the held object, the objects, and the drop zones.
*   `POST /arm/select_object`: Moves the arm to an object and picks it up. The request body should be a JSON object with the following format: `{"object": "object_name"}`.
*   `POST /arm/place_object`: Moves the arm to a drop zone and places the held object. The request body should be a JSON object with the following format: `{"drop_zone": "drop_zone_name"}`.
