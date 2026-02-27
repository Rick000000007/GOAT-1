package com.goatx.ansi

import com.goatx.terminal.TerminalGrid

object ParserFactory {

    fun create(grid: TerminalGrid): AnsiParser {
        return AnsiParser(grid)
    }
}