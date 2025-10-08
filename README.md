
# Edge Detection-OpenGL

## Overview
This project is a minimal Android app that captures camera frames, processes them using OpenCV in C++ via JNI, and displays the processed output using OpenGL ES 2.0. A small TypeScript-based web viewer is included to demonstrate receiving and displaying processed frames over WebSocket.


## Features Implemented

### Android App
- Camera feed capture using Camera2 API.
- JNI integration to process frames using OpenCV C++:
  - Grayscale conversion.
  - Canny edge detection.
- Real-time rendering of processed frames with OpenGL ES 2.0.
- Toggle button to switch between raw feed and edge-detected output.
- FPS counter displaying processing frame rate.

### Web Viewer
- TypeScript + HTML page that connects to the Android app over WebSocket.
- Displays the latest processed frame as a base64-encoded image.

# Screenshots
![WebView](https://photos.app.goo.gl/ktBGcnqc1NXsbL7F7)
![PhoneView](https://photos.app.goo.gl/7vjmj9HzAWZfLF759)


## Architecture & Folder Structure

/app
├── java/
│ ├── MainActivity.kt # Camera capture, GLSurfaceView setup, WebSocket server
│ └── GLRenderer.kt # OpenGL ES 2.0 renderer
├── cpp/
│ └── NativeLib.cpp # OpenCV processing via JNI
/web
├── index.html # Web viewer page
└── main.ts # TypeScript WebSocket client


**Notes:**
- OpenCV logic is implemented entirely in C++ (`NativeLib.cpp`) via JNI.
- OpenGL rendering is modularized in `GLRenderer.kt`.
- Web viewer connects via WebSocket to display processed frames.
- For simplicity, the `gl` folder is merged into the Java/Kotlin code folder.



## Setup Instructions

### Android
1. Install **Android Studio** with NDK and CMake support.
2. Clone this repository and open in Android Studio.
3. Sync Gradle to ensure NDK/C++ toolchain is detected.
4. Build and run on an Android device (minimum API 24 recommended).
5. Grant camera permission when prompted.

### Web
1. Open `web/index.html` in any modern browser.
2. Ensure the Android device and the web viewer machine are on the same network.
3. Update the WebSocket IP in `main.ts` if necessary (your Android device IP):
   ```typescript
   const ws = new WebSocket("ws://<ANDROID_IP>:8080");
Serve the web folder using a local server if needed:



## Frame Flow

 - Camera frame captured on Android device.
 - Frame sent to NativeLib.cpp via JNI.
 - OpenCV processes the frame (grayscale or edge-detected).
 - Processed frame returned to Android.
 - GLRenderer displays frame in real-time.
 - Frame sent over WebSocket to web viewer.



