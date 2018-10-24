package com.example.nishant.fenrir.util.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        var notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(notificationId, notification)
    }

    companion object {

        var NOTIFICATION_ID = "notification_id"
        var NOTIFICATION = "notification"
    }
}