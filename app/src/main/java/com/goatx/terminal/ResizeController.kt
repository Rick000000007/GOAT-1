package com.goatx.terminal

import android.graphics.Paint

class ResizeController(
    private val grid: TerminalGrid,
    private val engine: TerminalEngine
) {

    fun onSizeChanged(width: Int, height: Int, paint: Paint) {

        val cellWidth = paint.measureText("W")
        val fm = paint.fontMetrics
        val cellHeight = fm.descent - fm.ascent

        val cols = (width / cellWidth).toInt().coerceAtLeast(1)
        val rows = (height / cellHeight).toInt().coerceAtLeast(1)

        grid.rows = rows
        grid.cols = cols

        engine.resize(rows, cols)
    }
}