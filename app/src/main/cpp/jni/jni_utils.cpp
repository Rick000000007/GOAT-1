#include <android/log.h>

#define TAG "GOATX_NATIVE"

extern "C"
void native_log(const char* message) {
    __android_log_print(ANDROID_LOG_INFO, TAG, "%s", message);
}
