package com.goatx.ansi

import com.goatx.terminal.TerminalGrid

class AnsiParser(
    private val grid: TerminalGrid
) {

    private var state = EscapeState.NORMAL
    private val paramBuffer = StringBuilder()

    fun parse(input: String) {

        input.forEach { ch ->

            when (state) {

                EscapeState.NORMAL -> {
                    when (ch) {
                        '\u001B' -> state = EscapeState.ESCAPE
                        '\n' -> {
                            grid.cursor.row++
                            grid.newline()
                        }
                        '\r' -> grid.cursor.col = 0
                        else -> grid.put(ch.toString())
                    }
                }

                EscapeState.ESCAPE -> {
                    when (ch) {
                        '[' -> {
                            state = EscapeState.CSI
                            paramBuffer.clear()
                        }
                        else -> state = EscapeState.NORMAL
                    }
                }

                EscapeState.CSI -> {
                    if (ch.isLetter()) {

                        val params = parseParams(paramBuffer.toString())

                        if (ch == 'm') {
                            SgrHandler.apply(params, grid)
                        } else {
                            CsiHandler.handle(ch, params, grid)
                        }

                        state = EscapeState.NORMAL

                    } else {
                        paramBuffer.append(ch)
                    }
                }

                EscapeState.OSC -> {
                    state = EscapeState.NORMAL
                }
            }
        }

        grid.ensureBounds()
    }

    private fun parseParams(raw: String): List<Int> {
        if (raw.isBlank()) return emptyList()
        return raw.split(";")
            .mapNotNull { it.toIntOrNull() }
    }
}