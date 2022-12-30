package com.rootdown.dev.notesapp.root.lib.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast
import com.rootdown.dev.notesapp.root.di.util.IoDispatcher
import kotlinx.coroutines.*

class ReqETL : Service() {


    val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)

    private var startMode: Int = 0             // indicates how to behave if the service is killed
    private var binder: IBinder? = null        // interface for clients that bind
    private var allowRebind: Boolean = false   // indicates whether onRebind should be used


    override fun onCreate() {
        job.cancel()
        scope.launch {

        }

        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        job.cancel()
    }
}