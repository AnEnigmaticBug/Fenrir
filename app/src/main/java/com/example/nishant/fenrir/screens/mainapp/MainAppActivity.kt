package com.example.nishant.fenrir.screens.mainapp

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.nishant.fenrir.R
import com.example.nishant.fenrir.screens.mainapp.events.eventlist.EventListFragment
import com.example.nishant.fenrir.navigation.*

class MainAppActivity : NavHostActivity() {

    override val navViewId = R.id.navHostFRM

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.navHostFRM, EventListFragment())
                    .commit()
        }
    }
}
