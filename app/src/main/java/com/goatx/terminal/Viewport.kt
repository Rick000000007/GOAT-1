package com.goatx.terminal

class Viewport(
    private val grid: TerminalGrid
) {

    var scrollOffset = 0

    fun scrollUp(lines: Int = 1) {
        scrollOffset = (scrollOffset + lines)
            .coerceAtMost(grid.scrollback.all().size)
    }

    fun scrollDown(lines: Int = 1) {
        scrollOffset = (scrollOffset - lines)
            .coerceAtLeast(0)
    }

    fun visibleLines(): List<Array<Cell>> {
        val history = grid.scrollback.all()
        val combined = history + grid.grid

        val start = (combined.size - grid.rows - scrollOffset)
            .coerceAtLeast(0)

        val end = (start + grid.rows)
            .coerceAtMost(combined.size)

        return combined.subList(start, end)
    }
}
