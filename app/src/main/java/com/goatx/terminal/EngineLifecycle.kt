package com.goatx.terminal

class EngineLifecycle(
    private val engine: TerminalEngine
) {

    fun onStart(onUpdate: () -> Unit) {
        engine.start(onUpdate)
    }

    fun onStop() {
        engine.stop()
    }

    fun onResize(rows: Int, cols: Int) {
        engine.resize(rows, cols)
    }
}