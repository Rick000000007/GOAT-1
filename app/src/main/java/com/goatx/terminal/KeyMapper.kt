package com.goatx.terminal

import android.view.KeyEvent

object KeyMapper {

    fun map(event: KeyEvent): String? {

        if (event.action != KeyEvent.ACTION_DOWN) return null

        return when (event.keyCode) {

            KeyEvent.KEYCODE_ENTER -> "\n"
            KeyEvent.KEYCODE_DEL -> "\u007F"
            KeyEvent.KEYCODE_TAB -> "\t"

            KeyEvent.KEYCODE_DPAD_UP -> "\u001B[A"
            KeyEvent.KEYCODE_DPAD_DOWN -> "\u001B[B"
            KeyEvent.KEYCODE_DPAD_LEFT -> "\u001B[D"
            KeyEvent.KEYCODE_DPAD_RIGHT -> "\u001B[C"

            else -> {
                val unicode = event.unicodeChar
                if (unicode != 0) unicode.toChar().toString()
                else null
            }
        }
    }
}