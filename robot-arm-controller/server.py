from flask import Flask, jsonify, request
from flask_cors import CORS
from virtual_arm import VirtualArm
from kinematics import inverse_kinematics

app = Flask(__name__)
CORS(app)  # This will enable CORS for all routes

virtual_arm = VirtualArm()

# Our link lengths in cm
LINK1 = 18
LINK2 = 27

@app.route('/arm/state', methods=['GET'])
def get_arm_state():
    """Returns the current state of the virtual arm."""
    return jsonify({
        'motor_positions': virtual_arm.motor_positions,
        'motor_speeds': virtual_arm.motor_speeds,
        'ultrasonic_distance': virtual_arm.ultrasonic_distance,
        'object_in_view': virtual_arm.object_in_view,
        'object_centered': virtual_arm.object_centered,
        'object_in_quarter_frame': virtual_arm.object_in_quarter_frame
    })

@app.route('/arm/move', methods=['POST'])
def move_arm():
    """Moves the virtual arm."""
    data = request.get_json()
    message = data.get('message')

    if message is not None:
        try:
            # For simplicity, we'll use the message as the x-coordinate
            # and hardcode the y and z coordinates.
            x = float(message)
            y = 6.6
            z = 0.0

            # Calculate the new motor positions using inverse kinematics
            motor_values = inverse_kinematics([x, y, z], LINK1, LINK2)

            # Update the virtual arm's motor positions
            for i, motor_value in enumerate(motor_values):
                virtual_arm.set_position(i + 1, motor_value)

            return jsonify({'status': 'success'})
        except (ValueError, TypeError):
            return jsonify({'status': 'error', 'message': 'Invalid message format'}), 400
    else:
        return jsonify({'status': 'error', 'message': 'Missing message parameter'}), 400

if __name__ == '__main__':
    app.run(debug=True, port=5001)
