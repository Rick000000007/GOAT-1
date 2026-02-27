#pragma once

#include <jni.h>

extern int masterFd;
extern pid_t childPid;

extern "C" {

JNIEXPORT jint JNICALL
Java_com_goatx_nativebridge_NativePty_createPty(
        JNIEnv*, jobject);

JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_writePty(
        JNIEnv*, jobject, jbyteArray);

JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_destroyPty(
        JNIEnv*, jobject);

}
