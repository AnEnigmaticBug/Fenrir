package com.example.nishant.fenrir.domain.mainapp

import org.threeten.bp.LocalDateTime

data class PayloadedNotification(val id: String, val title: String, val body: String, val datetime: LocalDateTime)