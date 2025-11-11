let armState = {};

function setup() {
    createCanvas(windowWidth, windowHeight);
    fetchArmState();
    setInterval(fetchArmState, 1000); // Fetch the arm state every second
}

function draw() {
    background(220);
    if (armState.motor_positions) {
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

    // Draw gripper
    translate(link2Length, 0);
    rotate(elbowAngle);
    let gripperOpen = armState.motor_positions[4] === 100;
    if (gripperOpen) {
        line(0, -10, 20, -10);
        line(0, 10, 20, 10);
    } else {
        line(0, 0, 20, 0);
    }

    pop();
}
