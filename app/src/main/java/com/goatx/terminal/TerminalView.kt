package com.goatx.terminal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection

class TerminalView(context: Context) : View(context) {

    private val grid = TerminalGrid()
    private val viewport = Viewport(grid)
    private val renderer = TerminalRenderer(grid, viewport)

    private val paint = Paint().apply {
        isAntiAlias = true
        textSize = 36f
        color = Color.GREEN
    }

    private val engine = TerminalEngine(grid)

    init {
        isFocusableInTouchMode = true
        requestFocus()

        engine.start { postInvalidate() }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)
        renderer.render(canvas, paint)
        drawCursor(canvas)
    }

    private fun drawCursor(canvas: Canvas) {
        if (!grid.cursor.visible) return

        val cellWidth = paint.measureText("W")
        val fm = paint.fontMetrics
        val cellHeight = fm.descent - fm.ascent

        paint.color = Color.WHITE
        canvas.drawRect(
            grid.cursor.col * cellWidth,
            grid.cursor.row * cellHeight,
            (grid.cursor.col + 1) * cellWidth,
            (grid.cursor.row + 1) * cellHeight,
            paint
        )
    }

    override fun onCheckIsTextEditor(): Boolean = true

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.inputType = EditorInfo.TYPE_CLASS_TEXT
        outAttrs.imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
        return TerminalInputConnection(this, engine)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val mapped = KeyMapper.map(event)
        mapped?.let {
            engine.write(it)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
