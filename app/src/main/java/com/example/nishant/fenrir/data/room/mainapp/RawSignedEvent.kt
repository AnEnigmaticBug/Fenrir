package com.example.nishant.fenrir.data.room.mainapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "signedEvents")
data class RawSignedEvent(@PrimaryKey val id: String, val name: String, val numberOfTickets: Int)