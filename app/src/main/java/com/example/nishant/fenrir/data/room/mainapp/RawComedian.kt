package com.example.nishant.fenrir.data.room.mainapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "comedians")
data class RawComedian(@PrimaryKey val name: String, val profilePicURL: String, val hasVote: Boolean)