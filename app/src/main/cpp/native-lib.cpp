#include <jni.h>
#include <opencv2/opencv.hpp>

extern "C"
JNIEXPORT void JNICALL
Java_com_div_edgedetectionapp_NativeLib_processFrame(
    JNIEnv* env,
    jobject thiz,
    jbyteArray input,
    jint width,
    jint height,
    jbyteArray output
) {
    jbyte* inBytes = env->GetByteArrayElements(input, NULL);
    jbyte* outBytes = env->GetByteArrayElements(output, NULL);

    cv::Mat gray(height, width, CV_8UC1, reinterpret_cast<unsigned char*>(inBytes));

    cv::Mat edges;
    cv::Canny(gray, edges, 50, 150);

    memcpy(outBytes, edges.data, width * height);

    env->ReleaseByteArrayElements(input, inBytes, 0);
    env->ReleaseByteArrayElements(output, outBytes, 0);
}
