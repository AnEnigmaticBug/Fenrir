package com.example.nishant.fenrir.util.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.nishant.fenrir.data.room.AppDatabase
import com.example.nishant.fenrir.data.room.mainapp.RawPayloadedNotification
import com.example.nishant.fenrir.screens.splash.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.threeten.bp.LocalDateTime
import java.util.*

class FenrirFirebaseMessagingService : FirebaseMessagingService(){

    override fun onMessageReceived(message: RemoteMessage) {

        val title = message.data["title"] ?: ""
        val body = message.data["body"] ?: ""
        val date = Date(message.sentTime)

        sendNotification(title, body)
        Log.d("CANDY", "$title")
        val mainAppDao = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "fenrir.db").build().eventDao()
        mainAppDao.insertPayloadedNotification(RawPayloadedNotification(UUID.randomUUID().toString(), title, body, LocalDateTime.now()))
    }

    private fun sendNotification(title: String, body: String){
        val notificationBuilder = NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.stat_notify_more) //TODO(Change this icon)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(body))

        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtra("calledBy", "notification")
        val id = Integer.parseInt(System.currentTimeMillis().toString().reversed().substring(0, 9))
        intent.action = "$id"
        val activity = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_ONE_SHOT)
        notificationBuilder.setContentIntent(activity)

        val notification = notificationBuilder.build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }
}