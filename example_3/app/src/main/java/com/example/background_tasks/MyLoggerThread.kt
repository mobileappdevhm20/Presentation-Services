package com.example.background_tasks

import android.util.Log

class MyLoggerThread : Runnable {

    private var TAG = "MyLoggerThread"
    private var run = true

    override fun run() {
        while (run) {
            val current = System.currentTimeMillis()

            Log.i(TAG, "[$current] Doing hard work")
            Thread.sleep(1000)
        }
    }

    fun stop() {
        run = false
    }
}