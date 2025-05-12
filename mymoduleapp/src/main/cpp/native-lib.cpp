#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_mx_com_mymoduleapp_utls_NativeKeys_getTmdbToken(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("TU_TOKEN_AQUÍ");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_mx_com_mymoduleapp_utls_NativeKeys_getTmdbBaseUrl(JNIEnv* env, jobject thiz) {
    return env->NewStringUTF("TU_URL_AQUÍ");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_mx_com_mymoduleapp_utls_NativeKeys_getTmdbDataBaseName(JNIEnv* env, jobject thiz) {
    return env->NewStringUTF("movie-database");
}