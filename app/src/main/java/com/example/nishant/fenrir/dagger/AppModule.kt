package com.example.nishant.fenrir.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.example.nishant.fenrir.data.firestore.mainapp.FirestoreEventDatabase
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.CentralRepositoryImpl
import com.example.nishant.fenrir.data.repository.mainapp.EventRepository
import com.example.nishant.fenrir.data.repository.mainapp.FirestoreEventRepository
import com.example.nishant.fenrir.data.repository.wallet.StubWalletRepository
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.data.room.AppDatabase
import com.example.nishant.fenrir.data.room.mainapp.EventDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesWalletRepository(): WalletRepository = StubWalletRepository()

    @Provides @Singleton
    fun providesEventRepository(fsDb: FirestoreEventDatabase, eventDao: EventDao): EventRepository = FirestoreEventRepository(fsDb, eventDao)

    @Provides @Singleton
    fun providesFirestoreDatabase(): FirestoreEventDatabase = FirestoreEventDatabase()

    @Provides @Singleton
    fun providesEventDao(appDatabase: AppDatabase): EventDao = appDatabase.eventDao()

    @Provides @Singleton
    fun providesAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "fenrir.db").build()

    @Provides @Singleton
    fun providesCentralRepository(context: Context): CentralRepository = CentralRepositoryImpl(context)

    @Provides @Singleton
    fun providesContext(): Context = context
}