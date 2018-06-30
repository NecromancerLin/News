#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL
Java_com_necromancer_news_engine_JNI_getAppKey(JNIEnv *env, jobject instance) {
    //  AppKey
    std::string appkey="2192837df91e3b1786201395b329cae3";


    return env->NewStringUTF(appkey.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_necromancer_news_SplashActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
