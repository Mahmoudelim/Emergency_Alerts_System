package com.example.emergency_alert_system.track_location

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.emergencyalertsystem.R
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
        stopForeground(true)
        stopSelf()
    }

    private fun start() {
       val notification=NotificationCompat.Builder(this,"location").setContentTitle("Tracking Location")
           .setContentText("Location:null")
           .setSmallIcon(R.drawable.app_icon)
           .setOngoing(true)
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager



        locationClient.getLocationUpdate(1000L)
            .catch { e ->e.printStackTrace()}
            .onEach { location ->
                val lat=location.latitude.toString().takeLast(3)
                val long=location.longitude.toString().takeLast(3)
                val updatedNotification=notification.setContentText(
                    "Location:($lat , $long)"
                )
                notificationManager.notify(1,updatedNotification.build())
            }
            .launchIn(serviceScope)
        startForeground(1,notification.build())
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