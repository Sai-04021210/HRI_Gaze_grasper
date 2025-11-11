# Gaze-Grasper Visualization

This is a web-based 2D visualization of the Gaze-Grasper robotic arm. It communicates with the robot arm controller to display the arm's movements in real-time.

## Development server

To start the visualization, you will need to run a local web server in this directory. If you have Python 3 installed, you can do this by running the following command:

```bash
python -m http.server
```

Once the server is running, open your browser and navigate to `http://localhost:8000/`. The application will automatically display the visualization.

## Web-Based Controller

This visualization also includes a web-based controller that allows you to control the arm by clicking on objects and drop zones.

*   **To pick up an object:** Click on the object you want to pick up. The arm will move to the object and pick it up.
*   **To place an object:** While the arm is holding an object, click on one of the drop zones. The arm will move to the drop zone and place the object.
