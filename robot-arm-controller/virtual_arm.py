import time

class VirtualArm:
    def __init__(self):
        # Initialize the virtual arm's state
        self.motor_positions = {
            1: 180,  # Base servo
            2: 150,  # Shoulder servo
            3: 150,  # Elbow servo
            4: 100   # Gripper servo (100 for open, 220 for closed)
        }
        self.motor_speeds = {1: 300, 2: 300, 3: 300, 4: 300}
        self.ultrasonic_distance = 20  # cm
        self.object_in_view = True
        self.object_centered = False
        self.object_in_quarter_frame = True

    def set_position(self, motor_id, position):
        """Sets the position of a virtual motor."""
        print(f"Setting motor {motor_id} to position {position}")
        self.motor_positions[motor_id] = position
        time.sleep(0.1)  # Simulate the time it takes for the motor to move

    def get_position(self, motor_id):
        """Gets the position of a virtual motor."""
        return self.motor_positions[motor_id]

    def set_speed(self, motor_id, speed):
        """Sets the speed of a virtual motor."""
        print(f"Setting motor {motor_id} to speed {speed}")
        self.motor_speeds[motor_id] = speed

    def get_speed(self, motor_id):
        """Gets the speed of a virtual motor."""
        return self.motor_speeds[motor_id]

    def get_ultrasonic_data(self):
        """Gets the simulated ultrasonic sensor data."""
        return self.ultrasonic_distance

    def check_view(self):
        """Checks if an object is in the virtual camera's view."""
        return 1 if self.object_in_view else 0

    def find_center(self):
        """Simulates centering the camera on an object."""
        if self.object_centered:
            return 0  # Object is centered
        else:
            self.object_centered = True
            return 10  # Object is not centered, but we'll center it now

    def check_quarter_frame(self):
        """Simulates checking if an object is in a specific part of the frame."""
        if self.object_in_quarter_frame:
            return [True, 150, 160]  # [object_detected, frame_y_threshold, object_y]
        else:
            return [False]
