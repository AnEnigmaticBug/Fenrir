package com.example.nishant.fenrir.data.room.wallet

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "rawCartEntries")
data class RawCartEntry(@PrimaryKey val id: String, val itemId: String, val itemName: String, val itemPrice: Int, val stallId: String, val quantity: Int, val isValid: Boolean)