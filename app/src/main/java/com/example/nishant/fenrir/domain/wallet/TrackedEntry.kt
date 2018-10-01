package com.example.nishant.fenrir.domain.wallet

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TrackedEntry(val id: String, val orderId: String, val item: Item, val quantity: Int, val status: TrackingStatus, val orderDate: LocalDate, val orderTime: LocalTime)