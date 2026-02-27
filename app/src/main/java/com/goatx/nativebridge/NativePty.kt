package com.goatx.nativebridge

class NativePty {

    init {
        NativeLoader
    }

    external fun createPty(): Int
    external fun writePty(data: ByteArray)
    external fun resizePty(rows: Int, cols: Int)
    external fun destroyPty()
    external fun initSignalHandler()
    external fun startReadLoop(callback: PtyCallback)
}