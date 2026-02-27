package com.goatx.terminal

data class Cell(
    var char: Char = ' ',
    var fg: Int = 0xFFFFFFFF.toInt(),
    var bg: Int = 0xFF000000.toInt(),
    var bold: Boolean = false,
    var underline: Boolean = false,
    var inverse: Boolean = false
)