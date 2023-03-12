package com.example.emergency_alert_system.track_location

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.emergency_alert_system.user.creation.user_Login
import com.example.emergencyalertsystem.R
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
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
        var mAuth: FirebaseAuth




        locationClient.getLocationUpdate(1800000L)
            .catch { e ->e.printStackTrace()}
            .onEach { location ->
                val lat=location.latitude.toDouble()
                val long=location.longitude.toDouble()

                val location:CurrentLocation=CurrentLocation(lat,long)
                var firestore:FirebaseFirestore
                mAuth= FirebaseAuth.getInstance()
                firestore=FirebaseFirestore.getInstance()
                val UID =mAuth.currentUser!!.uid
                val userref=firestore.collection("USERS".trim()).document(UID).get().addOnSuccessListener { document ->
                    val nm = document.data!!["username".trim()].toString()

                    firestore.collection("USERS Adresses").document("$nm:Address")
                        .update("longitude", long, "latitude", lat)
                      firestore.collection("USERS Adresses").add(location)
                }
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