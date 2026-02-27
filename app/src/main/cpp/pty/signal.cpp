#include <signal.h>
#include <sys/wait.h>

void handle_sigchld(int) {
    int status;
    waitpid(-1, &status, WNOHANG);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_goatx_nativebridge_NativePty_initSignalHandler(
        JNIEnv*, jobject) {

    signal(SIGCHLD, handle_sigchld);
}
