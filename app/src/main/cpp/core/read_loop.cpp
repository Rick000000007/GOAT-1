#include <jni.h>
#include <unistd.h>
#include <thread>
#include <atomic>
#include <vector>
#include <android/log.h>
#include <sys/select.h>
#include "../pty/pty.h"

#define TAG "GOATX_READ_LOOP"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

extern int masterFd;

// Global JVM reference
static JavaVM* gJvm = nullptr;

// Global callback reference
static jobject gCallback = nullptr;

// Running flag
static std::atomic<bool> gRunning(false);

// Forward declaration
void readLoopThread();

extern "C"
JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_startReadLoop(
        JNIEnv* env,
        jobject /* thiz */,
        jobject callback) {

    if (gRunning.load()) {
        LOGI("Read loop already running");
        return;
    }

    // Store JVM reference
    env->GetJavaVM(&gJvm);

    // Create global reference to callback
    gCallback = env->NewGlobalRef(callback);

    gRunning.store(true);

    std::thread(readLoopThread).detach();

    LOGI("Read loop started");
}

extern "C"
JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_stopReadLoop(
        JNIEnv* env,
        jobject /* thiz */) {

    gRunning.store(false);

    if (gCallback != nullptr) {
        env->DeleteGlobalRef(gCallback);
        gCallback = nullptr;
    }

    LOGI("Read loop stopped");
}

void readLoopThread() {

    JNIEnv* env = nullptr;

    if (gJvm->AttachCurrentThread(&env, nullptr) != JNI_OK) {
        LOGE("Failed to attach thread to JVM");
        return;
    }

    jclass callbackClass = env->GetObjectClass(gCallback);
    jmethodID onDataMethod =
            env->GetMethodID(callbackClass, "onData", "([B)V");

    if (onDataMethod == nullptr) {
        LOGE("onData method not found");
        gJvm->DetachCurrentThread();
        return;
    }

    constexpr size_t BUFFER_SIZE = 4096;
    std::vector<char> buffer(BUFFER_SIZE);

    while (gRunning.load()) {

        if (masterFd < 0) {
            usleep(10000);
            continue;
        }

        fd_set readfds;
        FD_ZERO(&readfds);
        FD_SET(masterFd, &readfds);

        struct timeval timeout{};
        timeout.tv_sec = 1;
        timeout.tv_usec = 0;

        int ret = select(masterFd + 1, &readfds, nullptr, nullptr, &timeout);

        if (ret > 0 && FD_ISSET(masterFd, &readfds)) {

            ssize_t bytesRead =
                    read(masterFd, buffer.data(), BUFFER_SIZE);

            if (bytesRead > 0) {

                jbyteArray byteArray =
                        env->NewByteArray(bytesRead);

                env->SetByteArrayRegion(
                        byteArray,
                        0,
                        bytesRead,
                        reinterpret_cast<jbyte*>(buffer.data())
                );

                env->CallVoidMethod(
                        gCallback,
                        onDataMethod,
                        byteArray
                );

                env->DeleteLocalRef(byteArray);

            } else if (bytesRead == 0) {
                LOGI("PTY closed");
                break;
            } else {
                LOGE("Read error");
                break;
            }
        }
    }

    gJvm->DetachCurrentThread();

    LOGI("Read loop thread exited");
}