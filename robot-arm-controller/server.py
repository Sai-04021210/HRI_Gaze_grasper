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
    """Returns the current state of the virtual arm, objects, and drop zones."""
    return jsonify({
        'motor_positions': virtual_arm.motor_positions,
        'held_object': virtual_arm.held_object,
        'objects': virtual_arm.objects,
        'drop_zones': virtual_arm.drop_zones
    })

@app.route('/arm/select_object', methods=['POST'])
def select_object():
    """Moves the arm to an object and picks it up."""
    data = request.get_json()
    object_name = data.get('object')

    if object_name in virtual_arm.objects:
        object_coords = virtual_arm.objects[object_name]
        motor_values = inverse_kinematics([object_coords['x'], object_coords['y'], 0.0], LINK1, LINK2)
        for i, motor_value in enumerate(motor_values):
            virtual_arm.set_position(i + 1, motor_value)
        virtual_arm.pickup_object(object_name)
        return jsonify({'status': 'success'})
    else:
        return jsonify({'status': 'error', 'message': 'Invalid object name'}), 400

@app.route('/arm/place_object', methods=['POST'])
def place_object():
    """Moves the arm to a drop zone and places the held object."""
    data = request.get_json()
    drop_zone_name = data.get('drop_zone')

    if drop_zone_name in virtual_arm.drop_zones:
        drop_zone_coords = virtual_arm.drop_zones[drop_zone_name]
        motor_values = inverse_kinematics([drop_zone_coords['x'], drop_zone_coords['y'], 0.0], LINK1, LINK2)
        for i, motor_value in enumerate(motor_values):
            virtual_arm.set_position(i + 1, motor_value)
        virtual_arm.drop_object(drop_zone_name)
        return jsonify({'status': 'success'})
    else:
        return jsonify({'status': 'error', 'message': 'Invalid drop zone name'}), 400

if __name__ == '__main__':
    app.run(debug=True, port=5001)
