package com.goatx.terminal

class SessionManager {

    private val sessions = mutableListOf<TerminalEngine>()
    private var currentIndex = -1

    fun createSession(grid: TerminalGrid): TerminalEngine {
        val engine = TerminalEngine(grid)
        sessions.add(engine)
        currentIndex = sessions.lastIndex
        return engine
    }

    fun current(): TerminalEngine? {
        if (currentIndex < 0) return null
        return sessions[currentIndex]
    }

    fun switchTo(index: Int) {
        if (index in sessions.indices) {
            currentIndex = index
        }
    }

    fun closeCurrent() {
        current()?.stop()
        if (currentIndex >= 0) {
            sessions.removeAt(currentIndex)
            currentIndex = sessions.lastIndex
        }
    }
}