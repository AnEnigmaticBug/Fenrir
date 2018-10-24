package com.example.nishant.fenrir.util.notifications

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.example.nishant.fenrir.FenrirApp
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.screens.mainapp.MainAppActivity
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

class NotificationSchedulerImpl(private val context: Context) : NotificationScheduler {

    override fun scheduleNotification(id: Int, datetime: LocalDateTime, title: String, text: String) {
        if(datetime > LocalDateTime.now()) {
            val delay = LocalDateTime.now().until(datetime, ChronoUnit.MILLIS)

            val builder = NotificationCompat.Builder(context, FenrirApp.notificationChannelId)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.priority = NotificationManager.IMPORTANCE_HIGH
            }

            Log.d("CANDY", "delay: $delay   dt: $datetime cu: ${LocalDateTime.now()}")

            val intent = Intent(context, MainAppActivity::class.java)
            val activity = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            builder.setContentIntent(activity)

            val notification = builder.build()

            val notificationIntent = Intent(context, NotificationPublisher::class.java)
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, id)
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
            val pendingIntent =
                    PendingIntent.getBroadcast(context, id, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

            val futureInMillis = SystemClock.elapsedRealtime() + delay
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
        }
    }

    override fun cancelNotification(id: Int) {
        NotificationManagerCompat.from(context).cancel(id)
    }
}