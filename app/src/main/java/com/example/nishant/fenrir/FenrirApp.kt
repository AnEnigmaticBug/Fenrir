package com.example.nishant.fenrir

import android.app.Application
import com.example.nishant.fenrir.dagger.AppComponent
import com.example.nishant.fenrir.dagger.AppModule
import com.example.nishant.fenrir.dagger.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

class FenrirApp : Application() {

    companion object {

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}