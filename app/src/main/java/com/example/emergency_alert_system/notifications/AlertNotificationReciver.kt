package com.example.emergency_alert_system.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.emergency_alert_system.user.creation.user
import com.example.emergency_alert_system.user.model.Alert

class AlertNotificationReciver :BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service=AlertNotificationService(context)
        service.showNotification(user("mahmoud"), Alert("critical","his heart rate in dangerous case"))
    }
}