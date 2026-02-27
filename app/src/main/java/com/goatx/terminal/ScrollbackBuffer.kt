package com.goatx.terminal

import java.util.LinkedList

class ScrollbackBuffer(
    private val maxLines: Int
) {

    private val buffer = LinkedList<Array<Cell>>()

    fun push(line: Array<Cell>) {
        buffer.add(line.map { it.copy() }.toTypedArray())
        if (buffer.size > maxLines) {
            buffer.removeFirst()
        }
    }

    fun all(): List<Array<Cell>> = buffer
}