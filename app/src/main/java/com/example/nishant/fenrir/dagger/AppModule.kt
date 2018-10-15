package com.example.nishant.fenrir.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.example.nishant.fenrir.data.firestore.mainapp.FirestoreEventDatabase
import com.example.nishant.fenrir.data.firestore.wallet.FireTracker
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.CentralRepositoryImpl
import com.example.nishant.fenrir.data.repository.mainapp.EventRepository
import com.example.nishant.fenrir.data.repository.mainapp.FirestoreEventRepository
import com.example.nishant.fenrir.data.repository.wallet.FinalWalletRepository
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.data.retrofit.BaseInterceptor
import com.example.nishant.fenrir.data.retrofit.NetworkWatcher
import com.example.nishant.fenrir.data.retrofit.NetworkWatcherImpl
import com.example.nishant.fenrir.data.retrofit.WalletService
import com.example.nishant.fenrir.data.room.AppDatabase
import com.example.nishant.fenrir.data.room.mainapp.EventDao
import com.example.nishant.fenrir.data.room.wallet.WalletDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesWalletRepository(networkWatcher: NetworkWatcher, centralRepository: CentralRepository, walletService: WalletService, walletDao: WalletDao, fireTracker: FireTracker): WalletRepository = FinalWalletRepository(networkWatcher, centralRepository, walletService, walletDao, fireTracker)

    @Provides @Singleton
    fun providesWalletDao(appDatabase: AppDatabase): WalletDao = appDatabase.walletDao()

    @Provides @Singleton
    fun providesWalletService(retrofit: Retrofit): WalletService = retrofit.create(WalletService::class.java)

    @Provides @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("http://test.bits-oasis.org/")
            .client(OkHttpClient().newBuilder().addInterceptor(BaseInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides @Singleton
    fun providesFireTracker(centralRepository: CentralRepository): FireTracker = FireTracker(centralRepository)

    @Provides @Singleton
    fun providesNetworkWatcher(context: Context): NetworkWatcher = NetworkWatcherImpl(context)

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