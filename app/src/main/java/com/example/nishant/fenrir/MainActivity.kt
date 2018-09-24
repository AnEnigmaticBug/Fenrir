package com.example.nishant.fenrir

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.nishant.fenrir.screens.mainapp.events.eventlist.EventListFragment
import com.example.nishant.fenrir.navigation.*

class MainActivity : NavHostActivity() {

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
