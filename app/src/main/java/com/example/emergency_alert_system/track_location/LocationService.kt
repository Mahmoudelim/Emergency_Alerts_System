package com.example.emergency_alert_system.track_location

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.emergencyalertsystem.R
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.security.Provider.Service

class LocationService :android.app.Service() {
    lateinit var firestore:FirebaseFirestore
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
        firestore=FirebaseFirestore.getInstance()




        locationClient.getLocationUpdate(1800000L)
            .catch { e ->e.printStackTrace()}
            .onEach { location ->
                val lat=location.latitude.toString()
                val long=location.longitude.toString()

                val location:CurrentLocation=CurrentLocation(lat,long)
                firestore.collection("USERS").add(location)

            }
            .launchIn(serviceScope)

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