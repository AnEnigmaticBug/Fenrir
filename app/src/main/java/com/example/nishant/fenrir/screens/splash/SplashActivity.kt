package com.example.nishant.fenrir.screens.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nishant.fenrir.screens.mainapp.MainAppActivity
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().subscribeToTopic("all")

        startActivity(Intent(this, MainAppActivity::class.java))
        finish()
    }
}