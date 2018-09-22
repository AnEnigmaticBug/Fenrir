package com.example.nishant.fenrir.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.example.nishant.fenrir.data.*
import com.example.nishant.fenrir.data.firestore.FirestoreDatabase
import com.example.nishant.fenrir.data.room.AppDatabase
import com.example.nishant.fenrir.data.room.EventDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesEventRepository(fsDb: FirestoreDatabase, eventDao: EventDao): EventRepository = FirestoreEventRepository(fsDb, eventDao)

    @Provides @Singleton
    fun providesFirestoreDatabase(): FirestoreDatabase = FirestoreDatabase()

    @Provides @Singleton
    fun providesEventDao(appDatabase: AppDatabase): EventDao = appDatabase.eventDao()

    @Provides @Singleton
    fun providesAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "fenrir.db").build()

    @Provides @Singleton
    fun providesCentralRepository(context: Context): CentralRepository = CentralRepositoryImpl(context)

    @Provides @Singleton
    fun providesContext(): Context = context
}