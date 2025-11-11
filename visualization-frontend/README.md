# Gaze-Grasper Visualization

This is a web-based 2D visualization of the Gaze-Grasper robotic arm. It communicates with the robot arm controller to display the arm's movements in real-time.

## Development server

To start the visualization, you will need to run a local web server in this directory. If you have Python 3 installed, you can do this by running the following command:

```bash
python3 -m http.server 8000 --directory visualization-frontend
```

Or if you're already in the visualization-frontend directory:

```bash
python3 -m http.server 8000
```

**Note:** If you encounter an "Address already in use" error, check if another process is using port 8000:

```bash
lsof -i :8000
```

If needed, kill the existing process:

```bash
kill <PID>
```

Once the server is running, open your browser and navigate to `http://localhost:8000/`. The application will automatically display the visualization.
