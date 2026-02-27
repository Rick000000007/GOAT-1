package com.goatx.terminal

import android.view.View
import android.view.inputmethod.BaseInputConnection

class TerminalInputConnection(
    targetView: View,
    private val engine: TerminalEngine
) : BaseInputConnection(targetView, true) {

    override fun commitText(text: CharSequence?, newCursorPosition: Int): Boolean {
        text?.let { engine.write(it.toString()) }
        return true
    }

    override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
        engine.write("\u007F")
        return true
    }
}