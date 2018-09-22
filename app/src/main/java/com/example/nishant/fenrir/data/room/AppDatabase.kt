package com.example.nishant.fenrir.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [RawEvent::class], version = 1)
@TypeConverters(com.example.nishant.fenrir.data.room.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}