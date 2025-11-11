from flask import Flask, jsonify, request
from flask_cors import CORS
from virtual_arm import VirtualArm

app = Flask(__name__)
CORS(app)  # This will enable CORS for all routes

virtual_arm = VirtualArm()

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
    motor_id = data.get('motor_id')
    position = data.get('position')
    speed = data.get('speed')

    if motor_id is not None and position is not None:
        virtual_arm.set_position(int(motor_id), int(position))
    if motor_id is not None and speed is not None:
        virtual_arm.set_speed(int(motor_id), int(speed))

    return jsonify({'status': 'success'})

if __name__ == '__main__':
    app.run(debug=True, port=5001)
