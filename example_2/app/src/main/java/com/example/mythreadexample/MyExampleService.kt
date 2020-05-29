package com.example.mythreadexample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Thread.sleep

class MyExampleService : Service() {

    /*
     * Setup the api
     */

    private val apiBaseUrl = "http://192.168.178.65:8080/"
    private val retrofit: Retrofit
    private val timeService: TimeService
    private lateinit var myWorker: Thread
    lateinit var lastTimeRead: TimeService.CurrentTime

    init {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiBaseUrl)
            .build()
        timeService = retrofit.create(TimeService::class.java)
    }

    /*
     * When the service is created..
     */

    override fun onCreate() {
        super.onCreate()
        Log.i(MyExampleService::class.simpleName, "onCreate called")

        myWorker = Thread {
            while (!myWorker.isInterrupted) {

                // poll the api
                try {
                    val x = timeService.getTime().execute().body()
                    if (x != null) {
                        lastTimeRead = x
                    }
                    Log.i("MyExampleService", "Received response ${lastTimeRead.TimeString}")
                } catch (ex: IOException) {
                    Log.i("MyExampleService", ex.toString())
                }

                // sleep for a second
                try {
                    sleep(1000)
                } catch (ex: InterruptedException) {
                    break
                }
            }
        }
        myWorker.start()
    }

    /*
     * Binder
     */

    private val binder = MyExampleBinder()

    inner class MyExampleBinder : Binder() {
        fun getService(): MyExampleService = this@MyExampleService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    /*
     * Other lifecycle callbacks
     */

    override fun onDestroy() {
        super.onDestroy()
        Log.i(MyExampleService::class.simpleName, "onDestroy called")
        myWorker.interrupt()
    }
}