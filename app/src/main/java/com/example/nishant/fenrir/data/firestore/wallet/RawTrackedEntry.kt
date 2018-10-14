package com.example.nishant.fenrir.data.firestore.wallet

import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class RawTrackedEntry(val orderId: String, val itemId: String, val stallId: String, val quantity: Int, val status: TrackingStatus, val orderDate: LocalDate, val orderTime: LocalTime)