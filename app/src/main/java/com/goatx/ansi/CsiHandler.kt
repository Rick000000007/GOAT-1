package com.goatx.ansi

import com.goatx.terminal.TerminalGrid

object CsiHandler {

    fun handle(command: Char, params: List<Int>, grid: TerminalGrid) {

        val n = if (params.isEmpty()) 1 else params[0]

        when (command) {

            'A' -> grid.cursor.row -= n
            'B' -> grid.cursor.row += n
            'C' -> grid.cursor.col += n
            'D' -> grid.cursor.col -= n

            'H', 'f' -> {
                val row = if (params.size > 0) params[0] - 1 else 0
                val col = if (params.size > 1) params[1] - 1 else 0
                grid.cursor.row = row
                grid.cursor.col = col
            }

            'J' -> {
                when (params.firstOrNull() ?: 0) {
                    0 -> grid.clearScreen()
                    2 -> grid.clearScreen()
                }
            }

            'K' -> {
                val row = grid.cursor.row
                val col = grid.cursor.col
                for (c in col until grid.cols) {
                    grid.grid[row][c] =
                        com.goatx.terminal.Cell()
                }
            }
        }

        grid.ensureBounds()
    }
}
