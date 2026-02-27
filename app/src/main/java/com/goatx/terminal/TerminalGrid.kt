package com.goatx.terminal

import com.goatx.util.Constants
import com.goatx.util.MathUtil

class TerminalGrid(
    var rows: Int = Constants.DEFAULT_ROWS,
    var cols: Int = Constants.DEFAULT_COLS
) {

    var grid: Array<Array<Cell>> =
        Array(rows) { Array(cols) { Cell() } }

    val scrollback = ScrollbackBuffer(Constants.SCROLLBACK_LIMIT)
    val cursor = Cursor()

    var currentFg = ColorPalette.palette[2]
    var currentBg = ColorPalette.palette[0]
    var bold = false

    fun put(text: String) {
        if (cursor.col >= cols) newline()

        val cell = grid[cursor.row][cursor.col]
        cell.text = text
        cell.fg = currentFg
        cell.bg = currentBg
        cell.bold = bold

        cursor.col++
    }

    fun newline() {
        scrollback.push(grid[0])

        for (r in 1 until rows) {
            grid[r - 1] = grid[r]
        }

        grid[rows - 1] = Array(cols) { Cell() }

        cursor.col = 0
    }

    fun ensureBounds() {
        cursor.row = MathUtil.clamp(cursor.row, 0, rows - 1)
        cursor.col = MathUtil.clamp(cursor.col, 0, cols - 1)
    }

    fun clearScreen() {
        for (r in 0 until rows)
            for (c in 0 until cols)
                grid[r][c] = Cell()
    }
}