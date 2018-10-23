package com.example.nishant.fenrir.data.room.wallet

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items")
data class RawItem(@PrimaryKey val id: String, val name: String, val price: Int, val isAvailable: Boolean, val stallId: String)