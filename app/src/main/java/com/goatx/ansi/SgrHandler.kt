package com.goatx.ansi

import com.goatx.terminal.ColorPalette
import com.goatx.terminal.TerminalGrid

object SgrHandler {

    fun apply(params: List<Int>, grid: TerminalGrid) {

        if (params.isEmpty()) {
            reset(grid)
            return
        }

        var i = 0

        while (i < params.size) {

            when (val p = params[i]) {

                0 -> reset(grid)
                1 -> grid.bold = true

                in 30..37 ->
                    grid.currentFg =
                        ColorPalette.palette[p - 30]

                in 40..47 ->
                    grid.currentBg =
                        ColorPalette.palette[p - 40]

                38 -> {
                    if (params.getOrNull(i + 1) == 5) {
                        val color = params.getOrNull(i + 2) ?: 0
                        grid.currentFg =
                            ColorPalette.palette[color]
                        i += 2
                    }
                }

                48 -> {
                    if (params.getOrNull(i + 1) == 5) {
                        val color = params.getOrNull(i + 2) ?: 0
                        grid.currentBg =
                            ColorPalette.palette[color]
                        i += 2
                    }
                }
            }

            i++
        }
    }

    private fun reset(grid: TerminalGrid) {
        grid.currentFg = ColorPalette.palette[2]
        grid.currentBg = ColorPalette.palette[0]
        grid.bold = false
    }
}
