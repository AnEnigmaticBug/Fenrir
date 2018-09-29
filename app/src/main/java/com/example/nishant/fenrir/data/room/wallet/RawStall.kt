package com.example.nishant.fenrir.data.room.wallet

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "stalls")
data class RawStall(@PrimaryKey val id: String, val name: String, val description: String)