#include "pty.h"
#include <unistd.h>
#include <pty.h>
#include <signal.h>
#include <sys/ioctl.h>

int masterFd = -1;
pid_t childPid = -1;

JNIEXPORT jint JNICALL
Java_com_goatx_nativebridge_NativePty_createPty(
        JNIEnv*, jobject) {

    struct winsize ws{};
    ws.ws_row = 24;
    ws.ws_col = 80;

    childPid = forkpty(&masterFd, nullptr, nullptr, &ws);

    if (childPid == 0) {
        execl("/system/bin/sh", "sh", nullptr);
        _exit(1);
    }

    return masterFd;
}

JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_writePty(
        JNIEnv* env,
        jobject,
        jbyteArray data) {

    if (masterFd < 0) return;

    jsize len = env->GetArrayLength(data);
    jbyte* bytes = env->GetByteArrayElements(data, nullptr);

    write(masterFd, bytes, len);

    env->ReleaseByteArrayElements(data, bytes, 0);
}

JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_destroyPty(
        JNIEnv*, jobject) {

    if (childPid > 0) {
        kill(childPid, SIGKILL);
        childPid = -1;
    }

    if (masterFd >= 0) {
        close(masterFd);
        masterFd = -1;
    }
}
