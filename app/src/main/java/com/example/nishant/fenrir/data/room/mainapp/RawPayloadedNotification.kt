package com.example.nishant.fenrir.data.room.mainapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity(tableName = "payloadedNotifications")
class RawPayloadedNotification(@PrimaryKey val id: String, val title: String, val body: String, val datetime: LocalDateTime)