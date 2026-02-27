#include "pty.h"
#include <sys/ioctl.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_resizePty(
        JNIEnv*,
        jobject,
        jint rows,
        jint cols) {

    if (masterFd < 0) return;

    struct winsize ws{};
    ws.ws_row = rows;
    ws.ws_col = cols;

    ioctl(masterFd, TIOCSWINSZ, &ws);
}
