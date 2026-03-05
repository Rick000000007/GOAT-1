package com.goatx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goatx.nativebridge.NativeLoader
import com.goatx.nativebridge.NativePty
import com.goatx.terminal.TerminalView

class MainActivity : AppCompatActivity() {

    private lateinit var terminalView: TerminalView
    private lateinit var nativePty: NativePty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load native library
        NativeLoader.load()

        // Create terminal view
        terminalView = TerminalView(this)
        setContentView(terminalView)

        // Create PTY without lambda constructor
        nativePty = NativePty()

        // Start shell
        nativePty.startShell("/system/bin/sh")
    }

    override fun onDestroy() {
        nativePty.destroy()
        super.onDestroy()
    }
}
