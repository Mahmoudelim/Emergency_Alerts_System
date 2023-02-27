package com.example.emergency_alert_system.track_location

import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.security.Provider.Service

class LocationService :android.app.Service() {
    private val serviceScope= CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: user_Location
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient=UserDeafultLocation(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ActionStart->start()
            ActionStop->stop()
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun stop() {
        TODO("Not yet implemented")
    }

    private fun start() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object{
       const val ActionStart="Action Start"
       const val ActionStop="Action Stop"

   }

}