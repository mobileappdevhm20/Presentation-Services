package com.example.background_tasks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

// Source: https://androidwave.com/foreground-service-android-example-in-kotlin/

class MyForegroundService : Service() {

    private val CHANNEL_ID = "ForegroundService Kotlin"
    private val TAG = "MyForegroundService"
    private var myLoggerThread = MyLoggerThread()

    companion object {
        fun startService(context: Context) {
            val startIntent = Intent(context, MyForegroundService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, MyForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Called onStartCommand")
        // Create the notification
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Demo")
            .setContentText("My Service Demo")
            .setContentIntent(pendingIntent)
            .build()
        // Tell the system to run service in foreground
        startForeground(1, notification)

        // Start the logger thread in service
        Thread(myLoggerThread).start()
        // If service gets killed, do not recreate it unless there are pending intents to deliver
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.i(TAG, "Called onDestroy")
        // Stop logger thread
        myLoggerThread.stop()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}