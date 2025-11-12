# Screenshots Directory

This directory contains screenshots for the main README.md file.

## Required Screenshots:

### 1. `android-app-interface.png` ⏳ NEEDED
Capture the Android app showing:
- Gaze tracking active (red dot following gaze)
- The 5 colored interactive blocks:
  - Top row: Apple (red), Pen (teal), Lego (orange)
  - Bottom row: Drop-1 (purple), Drop-2 (green)
- Calibration screen (optional)

### 2. `web-visualization.png` ✅ COMPLETED
Capture the web interface (http://localhost:8000) showing:
- The 2D robot arm rendering
- Interactive objects (apple, pen, lego)
- Drop zones
- The arm in a position reaching for or holding an object

**Status**: Screenshot captured and saved!

### 3. `system-demo.png` ⏳ NEEDED
Capture a composite image or video screenshot showing:
- The complete system in action
- Android app + Web visualization side by side
- Demonstrating gaze-controlled object manipulation

## How to Capture Screenshots:

### For Android App:
1. Start the app on your Android device
2. Enable gaze tracking
3. Take a screenshot using device buttons (usually Power + Volume Down)
4. Transfer the screenshot to this directory and rename it to `android-app-interface.png`

### For Web Visualization:
1. Start the robot arm controller: `python3 server.py`
2. Start the web server: `python3 -m http.server 8000`
3. Open http://localhost:8000 in your browser
4. Use browser's screenshot tool or take a full-page screenshot
5. Save to this directory as `web-visualization.png`

### For System Demo:
1. Run both the Android app and web visualization simultaneously
2. Use screen recording or arrange windows side-by-side
3. Capture the interaction showing gaze → arm movement
4. Save as `system-demo.png`

## Alternative: Use Placeholder Images

If you don't have screenshots yet, you can create placeholder images or remove the screenshot section from the main README until you're ready.
