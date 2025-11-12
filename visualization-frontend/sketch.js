let armState = {};
let canvasWidth = 800;
let canvasHeight = 600;
let centerX, centerY;

function setup() {
    createCanvas(canvasWidth, canvasHeight);
    centerX = width / 2;
    centerY = height / 2;
    fetchArmState();
    setInterval(fetchArmState, 1000); // Fetch the arm state every second
}

function draw() {
    background(250);

    if (armState.motor_positions) {
        // Draw grid and coordinate system
        drawGrid();
        drawCoordinateAxes();

        // Draw workspace boundary circle (arm reach = 45cm)
        stroke(200, 100, 100, 100);
        strokeWeight(2);
        noFill();
        circle(centerX, centerY, 45 * 5 * 2); // radius = 45cm * scale factor

        drawDropZones();
        drawObjects();
        drawArm();
        drawLabels();
    } else {
        // Show connection status
        fill(255, 0, 0);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("Connecting to server...", width/2, height/2);
    }
}

function drawGrid() {
    stroke(220);
    strokeWeight(0.5);

    // Vertical grid lines (every 10cm in robot space = 50px)
    for (let x = centerX % 50; x < width; x += 50) {
        line(x, 0, x, height);
    }

    // Horizontal grid lines
    for (let y = centerY % 50; y < height; y += 50) {
        line(0, y, width, y);
    }
}

function drawCoordinateAxes() {
    // X-axis (red)
    stroke(255, 100, 100);
    strokeWeight(2);
    line(0, centerY, width, centerY);
    fill(255, 100, 100);
    noStroke();
    textSize(14);
    textAlign(RIGHT);
    text("X", width - 10, centerY - 10);

    // Y-axis (green)
    stroke(100, 255, 100);
    strokeWeight(2);
    line(centerX, 0, centerX, height);
    fill(100, 255, 100);
    noStroke();
    textAlign(CENTER);
    text("Y", centerX + 15, 20);

    // Draw axis labels with units
    fill(100);
    textSize(10);
    textAlign(CENTER);
    for (let i = -4; i <= 4; i++) {
        if (i !== 0) {
            text(i * 10 + "cm", centerX + i * 50, centerY + 15);
            text(i * 10 + "cm", centerX - 20, centerY - i * 50);
        }
    }
}

function fetchArmState() {
    fetch('http://localhost:5001/arm/state')
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(data => {
            armState = data;
        })
        .catch(error => {
            console.error('Error fetching arm state:', error);
            armState = {};
        });
}

function drawArm() {
    // Draw arm base at origin (center of canvas)
    let baseX = centerX;
    let baseY = centerY;

    // Draw base
    fill(50);
    noStroke();
    circle(baseX, baseY, 20);

    let baseAngle = radians(map(armState.motor_positions[1], 0, 360, 0, 360));
    let shoulderAngle = radians(map(armState.motor_positions[2], 0, 360, 0, 360));
    let elbowAngle = radians(map(armState.motor_positions[3], 0, 360, 0, 360));

    // Link lengths (18cm and 27cm scaled by 5)
    let link1Length = 18 * 5;
    let link2Length = 27 * 5;

    push();
    translate(baseX, baseY);
    rotate(baseAngle - HALF_PI); // Adjust to point upward initially

    // Draw link 1
    stroke(80, 80, 200);
    strokeWeight(8);
    line(0, 0, 0, -link1Length);

    // Draw shoulder joint
    fill(100);
    noStroke();
    circle(0, -link1Length, 15);

    // Draw link 2
    translate(0, -link1Length);
    rotate(shoulderAngle);
    stroke(200, 80, 80);
    strokeWeight(6);
    line(0, 0, 0, -link2Length);

    // Draw elbow joint
    fill(100);
    noStroke();
    circle(0, -link2Length, 12);

    // Draw gripper and held object
    translate(0, -link2Length);
    rotate(elbowAngle);

    let gripperOpen = armState.motor_positions[4] === 100;
    stroke(100);
    strokeWeight(4);
    if (gripperOpen) {
        line(-5, 0, -5, 20);
        line(5, 0, 5, 20);
    } else {
        line(-3, 0, -3, 15);
        line(3, 0, 3, 15);
        if (armState.held_object) {
            drawHeldObject(armState.held_object);
        }
    }

    pop();
}

function drawObjects() {
    if (!armState.objects) return;

    for (const [name, obj] of Object.entries(armState.objects)) {
        if (!obj.held) {
            let screenX = centerX + obj.x * 5;
            let screenY = centerY - obj.y * 5;

            push();
            translate(screenX, screenY);
            drawObject(name);

            // Draw label
            fill(0);
            textSize(12);
            textAlign(CENTER);
            text(name, 0, -25);
            pop();
        }
    }
}

function drawDropZones() {
    if (!armState.drop_zones) return;

    for (const [name, zone] of Object.entries(armState.drop_zones)) {
        let screenX = centerX + zone.x * 5;
        let screenY = centerY - zone.y * 5;

        push();
        translate(screenX, screenY);

        // Draw zone
        fill(150, 150, 200, 100);
        stroke(100, 100, 150);
        strokeWeight(2);
        rectMode(CENTER);
        rect(0, 0, 60, 60);

        // Draw label
        fill(0);
        noStroke();
        textSize(12);
        textAlign(CENTER);
        text(name, 0, 0);
        pop();
    }
}

function drawObject(name) {
    if (name === 'apple') {
        fill(255, 50, 50);
        stroke(200, 0, 0);
        strokeWeight(2);
        ellipse(0, 0, 30, 30);
        // Stem
        noStroke();
        fill(100, 200, 100);
        rect(-2, -15, 4, 8);
    } else if (name === 'pen') {
        fill(50, 150, 255);
        stroke(0, 100, 200);
        strokeWeight(2);
        rectMode(CENTER);
        rect(0, 0, 8, 35);
        // Pen tip
        fill(50);
        triangle(-4, 18, 4, 18, 0, 22);
    } else if (name === 'lego') {
        fill(100, 255, 100);
        stroke(50, 200, 50);
        strokeWeight(2);
        rectMode(CENTER);
        rect(0, 0, 30, 15);
        // Lego studs
        fill(80, 220, 80);
        noStroke();
        circle(-8, 0, 6);
        circle(8, 0, 6);
    }
}

function drawHeldObject(name) {
    push();
    translate(30, 0); // Position the object relative to the gripper
    drawObject(name);
    pop();
}

function drawLabels() {
    // Draw block ID labels
    fill(0);
    textSize(14);
    textAlign(LEFT);
    text("Android App Blocks:", 10, 20);
    textSize(12);
    text("Block 0 (Apple) - Red", 10, 40);
    text("Block 1 (Pen) - Teal", 10, 60);
    text("Block 2 (Lego) - Orange", 10, 80);
    text("Block 3 (Drop-1) - Purple", 10, 100);
    text("Block 4 (Drop-2) - Green", 10, 120);

    // Draw connection status
    if (armState.motor_positions) {
        fill(0, 200, 0);
        text("● Connected", 10, height - 20);
    } else {
        fill(200, 0, 0);
        text("● Disconnected", 10, height - 20);
    }
}

function mousePressed() {
    if (!armState.objects || !armState.drop_zones) return;

    // Check if an object was clicked
    for (const [name, obj] of Object.entries(armState.objects)) {
        if (obj.held) continue;

        let screenX = centerX + obj.x * 5;
        let screenY = centerY - obj.y * 5;

        if (dist(mouseX, mouseY, screenX, screenY) < 25) {
            selectObject(name);
            return;
        }
    }

    // Check if a drop zone was clicked
    for (const [name, zone] of Object.entries(armState.drop_zones)) {
        let screenX = centerX + zone.x * 5;
        let screenY = centerY - zone.y * 5;

        if (abs(mouseX - screenX) < 30 && abs(mouseY - screenY) < 30) {
            placeObject(name);
            return;
        }
    }
}

function selectObject(name) {
    // Map object names to block IDs
    const objectToBlockId = {
        'apple': 0,
        'pen': 1,
        'lego': 2
    };

    const blockId = objectToBlockId[name];
    if (blockId === undefined) return;

    fetch('http://localhost:5001/arm/move', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ block_id: blockId })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Select object response:', data);
        // Simulate picking up the object
        if (data.status === 'success') {
            armState.objects[name].held = true;
            armState.held_object = name;
        }
        fetchArmState();
    })
    .catch(error => console.error('Error selecting object:', error));
}

function placeObject(name) {
    // Map drop zone names to block IDs
    const dropZoneToBlockId = {
        'drop1': 3,
        'drop2': 4
    };

    const blockId = dropZoneToBlockId[name];
    if (blockId === undefined || !armState.held_object) return;

    fetch('http://localhost:5001/arm/move', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ block_id: blockId })
    })
    .then(response => response.json())
    .then(data => {
        console.log('Place object response:', data);
        // Simulate dropping the object
        if (data.status === 'success' && armState.held_object) {
            const droppedObj = armState.held_object;
            armState.objects[droppedObj].held = false;
            armState.objects[droppedObj].x = armState.drop_zones[name].x;
            armState.objects[droppedObj].y = armState.drop_zones[name].y;
            armState.held_object = null;
        }
        fetchArmState();
    })
    .catch(error => console.error('Error placing object:', error));
}
