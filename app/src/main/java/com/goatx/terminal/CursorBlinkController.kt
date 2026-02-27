package com.goatx.terminal

import android.os.Handler
import android.os.Looper

class CursorBlinkController(
    private val grid: TerminalGrid,
    private val invalidate: () -> Unit
) {

    private val handler = Handler(Looper.getMainLooper())

    private val blinkRunnable = object : Runnable {
        override fun run() {
            grid.cursor.visible = !grid.cursor.visible
            invalidate()
            handler.postDelayed(this, 600)
        }
    }

    fun start() {
        handler.post(blinkRunnable)
    }

    fun stop() {
        handler.removeCallbacks(blinkRunnable)
    }
}