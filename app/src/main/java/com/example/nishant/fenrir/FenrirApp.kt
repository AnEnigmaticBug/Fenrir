package com.example.nishant.fenrir

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.nishant.fenrir.dagger.AppComponent
import com.example.nishant.fenrir.dagger.AppModule
import com.example.nishant.fenrir.dagger.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

class FenrirApp : Application() {

    companion object {

        val notificationChannelId = "ID"
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        setupNotificationChannel()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(notificationChannelId, "Oasis18", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Oasis 18 Channel"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}