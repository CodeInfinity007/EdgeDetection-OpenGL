#include <jni.h>
#include <opencv2/opencv.hpp>
#include <cstring>
using namespace cv;

extern "C" {

JNIEXPORT void JNICALL
Java_com_div_edgedetectionapp_NativeLib_processFrame(
        JNIEnv* env,
        jobject,
        jbyteArray input,
        jint width,
        jint height,
        jbyteArray output) {

    jbyte* inputBytes = env->GetByteArrayElements(input, nullptr);
    jbyte* outputBytes = env->GetByteArrayElements(output, nullptr);


    Mat gray(height, width, CV_8UC1, reinterpret_cast<unsigned char*>(inputBytes));
    Mat edges;

    Canny(gray, edges, 80, 150);

    memcpy(outputBytes, edges.data, width * height);

    env->ReleaseByteArrayElements(input, inputBytes, 0);
    env->ReleaseByteArrayElements(output, outputBytes, 0);
}
}
