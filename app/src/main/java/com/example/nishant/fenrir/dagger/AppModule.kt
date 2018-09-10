package com.example.nishant.fenrir.dagger

import android.content.Context
import com.example.nishant.fenrir.data.CentralRepository
import com.example.nishant.fenrir.data.CentralRepositoryImpl
import com.example.nishant.fenrir.data.EventRepository
import com.example.nishant.fenrir.data.StubEventRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides @Singleton
    fun providesEventRepository(): EventRepository = StubEventRepositoryImpl()

    @Provides @Singleton
    fun providesCentralRepository(context: Context): CentralRepository = CentralRepositoryImpl(context)

    @Provides @Singleton
    fun providesContext(): Context = context
}