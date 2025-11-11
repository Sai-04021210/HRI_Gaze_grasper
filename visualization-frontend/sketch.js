let armState = {};

function setup() {
    createCanvas(windowWidth, windowHeight);
    fetchArmState();
    setInterval(fetchArmState, 1000); // Fetch the arm state every second
}

function draw() {
    background(220);
    if (armState.motor_positions) {
        drawDropZones();
        drawObjects();
        drawArm();
    }
}

function fetchArmState() {
    fetch('http://localhost:5001/arm/state')
        .then(response => response.json())
        .then(data => {
            armState = data;
        })
        .catch(error => console.error('Error fetching arm state:', error));
}

function drawArm() {
    // This is a simplified 2D representation of the arm
    let baseX = width / 2;
    let baseY = height - 50;

    let baseAngle = radians(map(armState.motor_positions[1], 0, 360, 0, 360));
    let shoulderAngle = radians(map(armState.motor_positions[2], 0, 360, 0, 360));
    let elbowAngle = radians(map(armState.motor_positions[3], 0, 360, 0, 360));

    let link1Length = 100;
    let link2Length = 150;

    push();
    translate(baseX, baseY);
    rotate(baseAngle);

    // Draw link 1
    stroke(0);
    strokeWeight(10);
    line(0, 0, link1Length, 0);

    // Draw link 2
    translate(link1Length, 0);
    rotate(shoulderAngle);
    line(0, 0, link2Length, 0);

    // Draw gripper and held object
    translate(link2Length, 0);
    rotate(elbowAngle);
    let gripperOpen = armState.motor_positions[4] === 100;
    if (gripperOpen) {
        line(0, -10, 20, -10);
        line(0, 10, 20, 10);
    } else {
        line(0, 0, 20, 0);
        if (armState.held_object) {
            drawHeldObject(armState.held_object);
        }
    }

    pop();
}

function drawObjects() {
    for (const [name, obj] of Object.entries(armState.objects)) {
        if (!obj.held) {
            push();
            translate(obj.x * 10, obj.y * 10);
            drawObject(name);
            pop();
        }
    }
}

function drawDropZones() {
    for (const [name, zone] of Object.entries(armState.drop_zones)) {
        push();
        translate(zone.x * 10, zone.y * 10);
        fill(150);
        rect(0, 0, 50, 50);
        fill(0);
        text(name, 10, 30);
        pop();
    }
}

function drawObject(name) {
    if (name === 'apple') {
        fill(255, 0, 0);
        ellipse(0, 0, 20, 20);
    } else if (name === 'pen') {
        fill(0, 0, 255);
        rect(0, 0, 5, 30);
    } else if (name === 'lego') {
        fill(0, 255, 0);
        rect(0, 0, 20, 10);
    }
}

function drawHeldObject(name) {
    push();
    translate(30, 0); // Position the object relative to the gripper
    drawObject(name);
    pop();
}

function mousePressed() {
    // Check if an object was clicked
    for (const [name, obj] of Object.entries(armState.objects)) {
        if (dist(mouseX, mouseY, obj.x * 10, obj.y * 10) < 20) {
            selectObject(name);
            return;
        }
    }

    // Check if a drop zone was clicked
    for (const [name, zone] of Object.entries(armState.drop_zones)) {
        if (mouseX > zone.x * 10 && mouseX < zone.x * 10 + 50 &&
            mouseY > zone.y * 10 && mouseY < zone.y * 10 + 50) {
            placeObject(name);
            return;
        }
    }
}

function selectObject(name) {
    fetch('http://localhost:5001/arm/select_object', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ object: name })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Select object response:', data);
        fetchArmState(); // Refresh the state after the action
    })
    .catch(error => console.error('Error selecting object:', error));
}

function placeObject(name) {
    fetch('http://localhost:5001/arm/place_object', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ drop_zone: name })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Place object response:', data);
        fetchArmState(); // Refresh the state after the action
    })
    .catch(error => console.error('Error placing object:', error));
}
