package com.example.nishant.fenrir.util.notifications

import org.threeten.bp.LocalDateTime

interface NotificationScheduler {

    fun scheduleNotification(id: Int, datetime: LocalDateTime, title: String, text: String)

    fun cancelNotification(id: Int)
}