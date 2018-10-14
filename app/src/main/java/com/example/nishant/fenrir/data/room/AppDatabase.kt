package com.example.nishant.fenrir.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.nishant.fenrir.data.room.mainapp.EventDao
import com.example.nishant.fenrir.data.room.mainapp.RawEvent
import com.example.nishant.fenrir.data.room.wallet.*

@Database(entities = [RawEvent::class, RawStall::class, RawItem::class, RawCartEntry::class, RawTrackedEntry::class], version = 1)
@TypeConverters(com.example.nishant.fenrir.data.room.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun walletDao(): WalletDao
}