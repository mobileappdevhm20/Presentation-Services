package com.example.mythreadexample

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /*
     * Connection to the service
     */

    private lateinit var exampleService: MyExampleService
    private var connected: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyExampleService.MyExampleBinder
            exampleService = binder.getService()
            connected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            connected = false
        }
    }

    /*
     * In the lifecycle of this activity..
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*
         * Use bound service
         */
        fab.setOnClickListener {
            if (connected)
                Toast.makeText(applicationContext, exampleService.lastTimeRead.TimeString, Toast.LENGTH_SHORT).show()
        }

        /*
         * Start the service
         */
        val intent = Intent(this, MyExampleService::class.java)
        startService(intent)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyExampleService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        connected = false
    }
}
