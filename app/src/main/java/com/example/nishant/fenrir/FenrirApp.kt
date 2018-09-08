package com.example.nishant.fenrir

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class FenrirApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}