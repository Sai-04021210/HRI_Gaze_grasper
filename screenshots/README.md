# Screenshots Directory

This directory contains screenshots for the main README.md file.

## Required Screenshots:

### 1. `android-app-interface.png` ✅ COMPLETED
Capture the Android app showing:
- Gaze tracking active (red dot following gaze)
- The 5 colored interactive blocks:
  - Top row: Apple (red), Pen (teal), Lego (orange)
  - Bottom row: Drop-1 (purple), Drop-2 (green)
- Calibration screen (optional)

**Status**: Screenshot captured and saved!

### 2. `web-visualization.png` ✅ COMPLETED
Capture the web interface (http://localhost:8000) showing:
- The 2D robot arm rendering
- Interactive objects (apple, pen, lego)
- Drop zones
- The arm in a position reaching for or holding an object

**Status**: Screenshot captured and saved!

### 3. `system-demo.gif` or `system-demo.mp4` ⏳ OPTIONAL
**This is optional** - You can add a GIF or video demonstration showing:
- The complete system in action
- Gaze interaction → Robot arm movement
- Object pickup and drop sequence
- Can be a screen recording converted to GIF or embedded video link

**Note**: If you want to add a demo video/GIF later, you can either:
- Place a GIF file here as `system-demo.gif`
- Link to a YouTube/Vimeo video in the README
- Skip this entirely - the two screenshots above are sufficient!

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
