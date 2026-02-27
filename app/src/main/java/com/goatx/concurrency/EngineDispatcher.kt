package com.goatx.concurrency

import java.util.concurrent.Executors

object EngineDispatcher {

    private val executor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "GOATX-Engine").apply {
            isDaemon = true
        }
    }

    fun execute(block: () -> Unit) {
        executor.execute(block)
    }
}