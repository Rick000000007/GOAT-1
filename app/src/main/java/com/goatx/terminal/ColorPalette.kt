package com.goatx.terminal

object ColorPalette {

    val palette: IntArray = IntArray(256)

    init {
        val base = intArrayOf(
            0xFF000000.toInt(),
            0xFF800000.toInt(),
            0xFF008000.toInt(),
            0xFF808000.toInt(),
            0xFF000080.toInt(),
            0xFF800080.toInt(),
            0xFF008080.toInt(),
            0xFFC0C0C0.toInt(),
            0xFF808080.toInt(),
            0xFFFF0000.toInt(),
            0xFF00FF00.toInt(),
            0xFFFFFF00.toInt(),
            0xFF0000FF.toInt(),
            0xFFFF00FF.toInt(),
            0xFF00FFFF.toInt(),
            0xFFFFFFFF.toInt()
        )

        for (i in 0..15) palette[i] = base[i]

        var index = 16
        for (r in 0..5)
            for (g in 0..5)
                for (b in 0..5) {
                    val red = if (r == 0) 0 else r * 40 + 55
                    val green = if (g == 0) 0 else g * 40 + 55
                    val blue = if (b == 0) 0 else b * 40 + 55
                    palette[index++] =
                        0xFF000000.toInt() or
                                (red shl 16) or
                                (green shl 8) or
                                blue
                }

        for (i in 0..23) {
            val gray = i * 10 + 8
            palette[index++] =
                0xFF000000.toInt() or
                        (gray shl 16) or
                        (gray shl 8) or
                        gray
        }
    }
}
