package com.goatx.terminal

import android.graphics.Canvas
import android.graphics.Paint

class TerminalRenderer(
    private val grid: TerminalGrid,
    private val viewport: Viewport
) {

    fun render(canvas: Canvas, paint: Paint) {

        val lines = viewport.visibleLines()

        val cellWidth = paint.measureText("W")
        val fm = paint.fontMetrics
        val cellHeight = fm.descent - fm.ascent

        lines.forEachIndexed { rowIndex, row ->

            var colIndex = 0

            while (colIndex < row.size) {

                val cell = row[colIndex]

                if (cell.width == 0) {
                    colIndex++
                    continue
                }

                // Background
                paint.color = cell.bg
                canvas.drawRect(
                    colIndex * cellWidth,
                    rowIndex * cellHeight,
                    (colIndex + cell.width) * cellWidth,
                    (rowIndex + 1) * cellHeight,
                    paint
                )

                // Foreground
                paint.color = cell.fg
                canvas.drawText(
                    cell.text,
                    colIndex * cellWidth,
                    rowIndex * cellHeight - fm.ascent,
                    paint
                )

                colIndex += cell.width
            }
        }
    }
}
