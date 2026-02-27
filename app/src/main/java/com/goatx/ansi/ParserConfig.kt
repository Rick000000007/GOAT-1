package com.goatx.ansi

data class ParserConfig(
    val enableAnsi: Boolean = true,
    val enable256Color: Boolean = true,
    val enableBold: Boolean = true
)