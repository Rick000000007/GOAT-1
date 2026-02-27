package com.goatx.terminal

data class Cursor(
    var row: Int = 0,
    var col: Int = 0,
    var visible: Boolean = true
)