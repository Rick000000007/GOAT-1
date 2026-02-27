#include <jni.h>
#include <android/log.h>
#include <atomic>
#include "../pty/pty.h"

#define TAG "GOATX_JNI"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

static JavaVM* gJvm = nullptr;

// Store class reference for safety
static jclass gNativePtyClass = nullptr;

jint JNI_OnLoad(JavaVM* vm, void*) {

    gJvm = vm;

    JNIEnv* env = nullptr;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        LOGE("JNI_OnLoad failed");
        return -1;
    }

    jclass localClass =
            env->FindClass("com/goatx/nativebridge/NativePty");

    if (!localClass) {
        LOGE("NativePty class not found");
        return -1;
    }

    gNativePtyClass =
            reinterpret_cast<jclass>(env->NewGlobalRef(localClass));

    env->DeleteLocalRef(localClass);

    LOGI("JNI Loaded Successfully");

    return JNI_VERSION_1_6;
}

void JNI_OnUnload(JavaVM* vm, void*) {

    JNIEnv* env = nullptr;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return;
    }

    if (gNativePtyClass != nullptr) {
        env->DeleteGlobalRef(gNativePtyClass);
        gNativePtyClass = nullptr;
    }

    LOGI("JNI Unloaded");
}