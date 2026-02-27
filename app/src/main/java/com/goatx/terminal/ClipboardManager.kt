package com.goatx.terminal

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class TerminalClipboard(context: Context) {

    private val clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE)
                as ClipboardManager

    fun copy(text: String) {
        clipboard.setPrimaryClip(
            ClipData.newPlainText("GOATX", text)
        )
    }

    fun paste(): String? {
        val clip = clipboard.primaryClip ?: return null
        if (clip.itemCount == 0) return null
        return clip.getItemAt(0).coerceToText(null).toString()
    }
}