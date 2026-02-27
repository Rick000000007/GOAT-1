package com.goatx.ansi

enum class EscapeState {
    NORMAL,
    ESCAPE,
    CSI,
    OSC
}
