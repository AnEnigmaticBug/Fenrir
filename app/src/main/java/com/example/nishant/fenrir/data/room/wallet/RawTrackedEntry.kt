package com.example.nishant.fenrir.data.room.wallet

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.nishant.fenrir.domain.wallet.TrackingStatus
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Entity(tableName = "trackedEntries")
data class RawTrackedEntry(@PrimaryKey val id: String, val orderId: String, val itemId: String, val quantity: Int, val status: TrackingStatus, val orderDate: LocalDate, val orderTime: LocalTime)