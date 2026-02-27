package com.goatx.nativebridge

interface PtyCallback {
    fun onData(data: ByteArray)
}