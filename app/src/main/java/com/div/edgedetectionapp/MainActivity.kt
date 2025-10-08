package com.div.edgedetectionapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.opengl.GLSurfaceView
import android.media.ImageReader
import android.graphics.ImageFormat
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var renderer: GLRenderer
    private lateinit var imageReader: ImageReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val glSurfaceView = findViewById<GLSurfaceView>(R.id.gl_surface_view)
        renderer = GLRenderer()
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(renderer)
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        val toggleButton = findViewById<Button>(R.id.toggleButton)
        toggleButton.setOnClickListener {
            renderer.showEdges = !renderer.showEdges
        }

        val fpsText = findViewById<TextView>(R.id.fpsText)
        var lastTime = System.currentTimeMillis()
        var frameCount = 0

        val width = 640
        val height = 480
        imageReader = ImageReader.newInstance(width, height, ImageFormat.YUV_420_888, 2)
        imageReader.setOnImageAvailableListener({ reader ->
            val image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener
            val yPlane = image.planes[0]
            val buffer = yPlane.buffer
            val rowStride = yPlane.rowStride
            val padded = ByteArray(rowStride * height)
            buffer.get(padded)
            val inputArray = ByteArray(width * height)
            for (r in 0 until height) {
                System.arraycopy(padded, r * rowStride, inputArray, r * width, width)
            }

            val outputArray = ByteArray(width * height)
            NativeLib.processFrame(inputArray, width, height, outputArray)

            val displayArray = if (renderer.showEdges) outputArray else inputArray
            renderer.updateFrame(displayArray, width, height)

            frameCount++
            val now = System.currentTimeMillis()
            if (now - lastTime >= 1000) {
                val fps = frameCount * 1000 / (now - lastTime)
                runOnUiThread { fpsText.text = "FPS: $fps" }
                frameCount = 0
                lastTime = now
            }

            glSurfaceView.requestRender()
            image.close()
        }, null)
    }
}
