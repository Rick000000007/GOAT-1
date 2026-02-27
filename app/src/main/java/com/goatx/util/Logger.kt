package com.goatx.util

import android.util.Log

object Logger {

    private const val DEBUG = true

    fun d(message: String) {
        if (DEBUG) Log.d(Constants.TAG, message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        Log.e(Constants.TAG, message, throwable)
    }
}
