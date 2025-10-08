#include <jni.h>
#include <opencv2/opencv.hpp>

extern "C" JNIEXPORT void JNICALL
Java_com_div_edgedetectionapp_NativeLib_processFrame(
    JNIEnv* env,
    jobject thiz,
    jbyteArray input,
    jint width,
    jint height,
    jbyteArray output) {
    // TODO
}
