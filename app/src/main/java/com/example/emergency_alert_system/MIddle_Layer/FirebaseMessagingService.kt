package com.example.emergency_alert_system.MIddle_Layer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import com.example.emergency_alert_system.MainActivity
import com.example.emergencyalertsystem.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId="notification_channel"
const val channelName="com.example.emergency_alert_system.MIddle_Layer"
class firebaseMessagingService :FirebaseMessagingService(){
@SuppressLint("RemoteViewLayout")
fun getRemoteView(title: String, message: String):RemoteViews{
 val remoteView=RemoteViews("com.example.emergency_alert_system.MIddle_Layer",R.layout.notification)
remoteView.setTextViewText(R.id.not_title,title)
 remoteView.setTextViewText(R.id.not_message,message)
 remoteView.setImageViewResource(R.id.notification_image,R.drawable.app_icon)
return remoteView
}

 override fun onMessageReceived(message: RemoteMessage) {
  if (message.getNotification() !=null)
  {
   generateNotification(message.notification!!.title!!,message.notification!!.body!!)

  }
 }

 fun generateNotification(title:String,message:String){
  val intent=Intent(this,MainActivity::class.java)
  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
  val pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
  var builder:NotificationCompat.Builder=NotificationCompat.Builder(applicationContext, channelId)
   .setSmallIcon(R.drawable.app_icon)
   .setAutoCancel(true)
   .setVibrate(longArrayOf(1000,1000,1000,1000))
   .setOnlyAlertOnce(true)
   .setContentIntent(pendingIntent)
  builder=builder.setContent(getRemoteView(title,message))
  val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
  if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
  {
   val notificationChannel=NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
   notificationManager.createNotificationChannel(notificationChannel)
  }
  notificationManager.notify(0,builder.build())

 }

}