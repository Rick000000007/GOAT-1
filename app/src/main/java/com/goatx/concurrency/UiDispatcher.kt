package com.goatx.concurrency

import android.os.Handler
import android.os.Looper

object UiDispatcher {

    private val handler = Handler(Looper.getMainLooper())

    fun post(task: () -> Unit) {
        handler.post(task)
    }
}
