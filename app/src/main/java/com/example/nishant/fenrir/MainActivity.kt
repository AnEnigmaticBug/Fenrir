package com.example.nishant.fenrir

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.nishant.fenrir.screens.events.eventlist.EventListFragment
import com.example.nishant.fenrir.navigation.*
import com.example.nishant.fenrir.screens.splash.SplashFragment
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class MainActivity : NavHostActivity() {

    override val navViewId = R.id.navHostFRM

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.navHostFRM, SplashFragment())
                    .commit()
            Flowable.timer(1500, TimeUnit.MILLISECONDS)
                    .subscribe {
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.navHostFRM, EventListFragment())
                                .commit()
                    }
        }
    }
}
