package com.goatx.terminal

import com.goatx.ansi.AnsiParser
import com.goatx.concurrency.EngineDispatcher
import com.goatx.nativebridge.NativePty
import com.goatx.nativebridge.PtyCallback

class TerminalEngine(
    private val grid: TerminalGrid
) {

    private val pty = NativePty()
    private val parser = AnsiParser(grid)

    private var started = false

    fun start(onUpdate: () -> Unit) {

        if (started) return
        started = true

        pty.initSignalHandler()
        pty.createPty()

        pty.startReadLoop(object : PtyCallback {
            override fun onData(data: ByteArray) {

                EngineDispatcher.execute {
                    parser.parse(String(data))
                    onUpdate()
                }
            }
        })
    }

    fun write(text: String) {
        if (!started) return
        pty.writePty(text.toByteArray())
    }

    fun resize(rows: Int, cols: Int) {
        if (!started) return
        pty.resizePty(rows, cols)
    }

    fun stop() {
        if (!started) return
        pty.destroyPty()
        started = false
    }
}
