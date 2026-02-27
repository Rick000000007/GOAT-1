package com.goatx.terminal

class SelectionManager {

    var startRow = -1
    var startCol = -1
    var endRow = -1
    var endCol = -1

    fun clear() {
        startRow = -1
        startCol = -1
        endRow = -1
        endCol = -1
    }

    fun isActive(): Boolean {
        return startRow >= 0 && endRow >= 0
    }

    fun extract(lines: List<Array<Cell>>): String {
        if (!isActive()) return ""

        val builder = StringBuilder()

        for (r in startRow..endRow) {
            val row = lines.getOrNull(r) ?: continue
            val start = if (r == startRow) startCol else 0
            val end = if (r == endRow) endCol else row.size - 1

            for (c in start..end) {
                builder.append(row[c].text)
            }
            builder.append("\n")
        }

        return builder.toString()
    }
}
