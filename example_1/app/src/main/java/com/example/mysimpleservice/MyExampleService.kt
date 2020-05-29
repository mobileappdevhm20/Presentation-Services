package com.example.mysimpleservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyExampleService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.i(MyExampleService::class.simpleName, "onCreate called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(MyExampleService::class.simpleName, "onStartCommand called")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {

        /*
         * this is not a bound-service, e.g. binding is not supported.
         */

        return null
    }
}