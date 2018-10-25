package com.example.nishant.fenrir.dagger

import android.arch.persistence.room.Room
import android.content.Context
import com.example.nishant.fenrir.data.firestore.mainapp.FirestoreEventDatabase
import com.example.nishant.fenrir.data.firestore.mainapp.N2OManager
import com.example.nishant.fenrir.data.firestore.wallet.FireTracker
import com.example.nishant.fenrir.data.firestore.wallet.FireAccountant
import com.example.nishant.fenrir.data.repository.CentralRepository
import com.example.nishant.fenrir.data.repository.CentralRepositoryImpl
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepository
import com.example.nishant.fenrir.data.repository.mainapp.MainAppRepositoryImpl
import com.example.nishant.fenrir.data.repository.mainapp.LoginRepository
import com.example.nishant.fenrir.data.repository.mainapp.LoginRepositoryImpl
import com.example.nishant.fenrir.data.repository.wallet.FinalWalletRepository
import com.example.nishant.fenrir.data.repository.wallet.WalletRepository
import com.example.nishant.fenrir.data.retrofit.BaseInterceptor
import com.example.nishant.fenrir.data.retrofit.NetworkWatcher
import com.example.nishant.fenrir.data.retrofit.NetworkWatcherImpl
import com.example.nishant.fenrir.data.retrofit.mainapp.LoginService
import com.example.nishant.fenrir.data.retrofit.mainapp.MainAppService
import com.example.nishant.fenrir.data.retrofit.wallet.WalletService
import com.example.nishant.fenrir.data.room.AppDatabase
import com.example.nishant.fenrir.data.room.mainapp.MainAppDao
import com.example.nishant.fenrir.data.room.wallet.WalletDao
import com.example.nishant.fenrir.util.notifications.NotificationScheduler
import com.example.nishant.fenrir.util.notifications.NotificationSchedulerImpl
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
    fun providesWalletRepository(networkWatcher: NetworkWatcher, centralRepository: CentralRepository, fireAccountant: FireAccountant, walletService: WalletService, walletDao: WalletDao, fireTracker: FireTracker): WalletRepository = FinalWalletRepository(networkWatcher, centralRepository, fireAccountant, walletService, walletDao, fireTracker)

    @Provides @Singleton
    fun providesWalletDao(appDatabase: AppDatabase): WalletDao = appDatabase.walletDao()

    @Provides @Singleton
    fun providesWalletService(retrofit: Retrofit): WalletService = retrofit.create(WalletService::class.java)

    @Provides @Singleton
    fun providesFireAccountant(centralRepository: CentralRepository): FireAccountant = FireAccountant(centralRepository)

    @Provides @Singleton
    fun providesLoginRepository(networkWatcher: NetworkWatcher, centralRepository: CentralRepository, loginService: LoginService): LoginRepository = LoginRepositoryImpl(networkWatcher, centralRepository, loginService)

    @Provides @Singleton
    fun providesLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Provides @Singleton
    fun providesFireTracker(centralRepository: CentralRepository): FireTracker = FireTracker(centralRepository)

    @Provides @Singleton
    fun providesNetworkWatcher(context: Context): NetworkWatcher = NetworkWatcherImpl(context)

    @Provides @Singleton
    fun providesMainAppRepository(fsDb: FirestoreEventDatabase, n2OManager: N2OManager, centralRepository: CentralRepository, networkWatcher: NetworkWatcher, mainAppService: MainAppService, mainAppDao: MainAppDao): MainAppRepository = MainAppRepositoryImpl(fsDb, n2OManager, centralRepository, networkWatcher, mainAppService, mainAppDao)

    @Provides @Singleton
    fun providesMainAppService(retrofit: Retrofit): MainAppService = retrofit.create(MainAppService::class.java)

    @Provides @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("http://test.bits-oasis.org/")
            .client(OkHttpClient().newBuilder().addInterceptor(BaseInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides @Singleton
    fun providesN2OWatcher(): N2OManager = N2OManager()

    @Provides @Singleton
    fun providesFirestoreDatabase(): FirestoreEventDatabase = FirestoreEventDatabase()

    @Provides @Singleton
    fun providesEventDao(appDatabase: AppDatabase): MainAppDao = appDatabase.eventDao()

    @Provides @Singleton
    fun providesNotificationScheduler(context: Context): NotificationScheduler = NotificationSchedulerImpl(context)

    @Provides @Singleton
    fun providesAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "fenrir.db").build()

    @Provides @Singleton
    fun providesCentralRepository(context: Context): CentralRepository = CentralRepositoryImpl(context)

    @Provides @Singleton
    fun providesContext(): Context = context
}