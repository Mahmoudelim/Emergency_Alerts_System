package com.example.emergency_alert_system.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class myApp :Application(){

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            val channel=NotificationChannel(
            AlertNotificationService.Alert_Channel_Id,
            "Alert Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
            )
            val channel2=NotificationChannel(
              "location",
                "Location",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.description="This used for tracking location"
            val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            notificationManager.createNotificationChannel(channel2)
        }

    }
}