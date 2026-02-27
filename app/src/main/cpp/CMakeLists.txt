cmake_minimum_required(VERSION 3.22.1)

project(goatx_native)

add_library(
        goatx_native
        SHARED
        pty/pty.cpp
        pty/resize.cpp
        pty/signal.cpp
        core/read_loop.cpp
        core/buffer.cpp
        jni/jni_bridge.cpp
        jni/jni_utils.cpp
)

find_library(log-lib log)

target_link_libraries(
        goatx_native
        ${log-lib}
)
