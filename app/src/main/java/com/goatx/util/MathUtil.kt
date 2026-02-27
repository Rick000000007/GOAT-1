package com.goatx.util

object MathUtil {

    fun clamp(value: Int, min: Int, max: Int): Int {
        return when {
            value < min -> min
            value > max -> max
            else -> value
        }
    }

    fun safeMin(a: Int, b: Int): Int = if (a < b) a else b
}
