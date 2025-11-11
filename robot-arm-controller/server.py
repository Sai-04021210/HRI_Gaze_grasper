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
    print(f"Received data: {data}")  # Debug: see what we're receiving
    message = data.get('message')
    block_id = data.get('block_id')
    print(f"Extracted - block_id: {block_id}, message: {message}")  # Debug

    # Map block IDs to different robot arm positions (x, y, z)
    # Valid range: x can be positive or negative, y > 0 (arm length = 18+27=45cm)
    # Blocks 0-2: Pick positions (objects at different locations)
    # Blocks 3-4: Drop positions (arm moves down to drop, tip facing down)
    block_positions = {
        # Pick targets (horizontal reach)
        0: [30.0, 15.0, 0.0],    # Apple - right side pick
        1: [0.0, 25.0, 0.0],     # Pen - center pick (low)
        2: [-30.0, 15.0, 0.0],   # Lego - left side pick
        # Drop locations (arm extended down, tip pointing down)
        3: [0.0, 35.0, 0.0],     # Drop-1 - center drop zone (arm down)
        4: [20.0, 25.0, 0.0]     # Drop-2 - right drop zone (arm down)
    }

    try:
        if block_id is not None:
            # Use predefined position for this block ID
            if block_id in block_positions:
                x, y, z = block_positions[block_id]
                print(f"Moving to block {block_id}: position ({x}, {y}, {z})")
            else:
                return jsonify({'status': 'error', 'message': f'Unknown block_id: {block_id}'}), 400
        elif message is not None:
            # Fallback: use message as x-coordinate (for backwards compatibility)
            x = float(message)
            y = 6.6
            z = 0.0
        else:
            return jsonify({'status': 'error', 'message': 'Missing message or block_id parameter'}), 400

        # Calculate the new motor positions using inverse kinematics
        motor_values = inverse_kinematics([x, y, z], LINK1, LINK2)

        # Update the virtual arm's motor positions
        for i, motor_value in enumerate(motor_values):
            virtual_arm.set_position(i + 1, motor_value)

        return jsonify({'status': 'success', 'position': [x, y, z]})
    except (ValueError, TypeError) as e:
        return jsonify({'status': 'error', 'message': f'Invalid format: {str(e)}'}), 400

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5001)
