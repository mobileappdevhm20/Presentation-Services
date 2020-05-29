package com.example.background_tasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var myLoggerThread :MyLoggerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startThreadButton = findViewById(R.id.start_thread) as Button
        val stopThreadButton = findViewById(R.id.stop_thread) as Button
        val startServiceButton = findViewById(R.id.start_service) as Button
        val stopServiceButton = findViewById(R.id.stop_service) as Button

        startThreadButton.setOnClickListener {
            Log.i(TAG, "Start MyLoggerThread in Main")
            myLoggerThread = MyLoggerThread()
            Thread(myLoggerThread).start()
        }

        stopThreadButton.setOnClickListener {
            Log.i(TAG, "Stop MyLoggerThread in Main")
            myLoggerThread.stop()
        }

        startServiceButton.setOnClickListener {
            Log.i(TAG, "Start MyForegroundService in Main")
            MyForegroundService.startService(this)
        }

        stopServiceButton.setOnClickListener {
            Log.i(TAG, "Stop MyForegroundService in Main")
            MyForegroundService.stopService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Called onDestroy")
    }
}
