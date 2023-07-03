package com.example.emergency_alert_system.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.emergency_alert_system.user.Alerts.Alerts
import com.example.emergency_alert_system.user.creation.user
import com.example.emergency_alert_system.user.model.Alert
import com.example.emergencyalertsystem.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class AlertNotificationService (private val context:Context){
    val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager

    fun showNotification(user: user,alert: Alert){
        val activityIntent=Intent(context,Alerts::class.java)
        val activityPendingIntent=PendingIntent.getActivity(context,1,activityIntent,
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        val notification=NotificationCompat.Builder(context, Alert_Channel_Id)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("${alert.Classification} Alert From ${user.fullname}")
            .setContentText("${alert.description}")
            .setContentIntent(activityPendingIntent)
            .build()
        notificationManager.notify(1,notification)
    }
    companion object{
        const val Alert_Channel_Id="Alert_channel"

    }
}