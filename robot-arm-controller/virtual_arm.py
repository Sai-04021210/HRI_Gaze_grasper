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
        self.held_object = None

        # Define the objects and drop zones
        # Spread across canvas: left to right positioning
        self.objects = {
            'apple': {'x': -40, 'y': 30, 'held': False},   # Left side
            'pen': {'x': 0, 'y': 30, 'held': False},       # Center
            'lego': {'x': 40, 'y': 30, 'held': False}      # Right side
        }
        self.drop_zones = {
            'drop1': {'x': -30, 'y': -20},   # Upper left
            'drop2': {'x': 30, 'y': -20}     # Upper right
        }

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

    def pickup_object(self, object_name):
        """Picks up an object."""
        if self.held_object is None:
            self.held_object = object_name
            self.objects[object_name]['held'] = True
            self.motor_positions[4] = 220  # Close gripper

    def drop_object(self, drop_zone_name):
        """Drops the held object."""
        if self.held_object is not None:
            self.objects[self.held_object]['x'] = self.drop_zones[drop_zone_name]['x']
            self.objects[self.held_object]['y'] = self.drop_zones[drop_zone_name]['y']
            self.objects[self.held_object]['held'] = False
            self.held_object = None
            self.motor_positions[4] = 100  # Open gripper
