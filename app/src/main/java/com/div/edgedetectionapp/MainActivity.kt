package com.div.edgedetectionapp

import android.os.Bundle
import android.app.Activity
import android.media.ImageReader
import android.media.Image
import android.graphics.ImageFormat

class MainActivity : Activity() {
    private lateinit var imageReader: ImageReader
    private val width = 640
    private val height = 480

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageReader = ImageReader.newInstance(width, height, ImageFormat.YUV_420_888, 2)
        imageReader.setOnImageAvailableListener({ reader ->
            val image: Image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener

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

            image.close()
        }, null)
    }
}
