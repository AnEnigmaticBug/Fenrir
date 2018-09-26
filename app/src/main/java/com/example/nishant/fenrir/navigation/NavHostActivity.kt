package com.example.nishant.fenrir.navigation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.nishant.fenrir.screens.mainapp.MainAppActivity
import com.example.nishant.fenrir.screens.mainapp.events.eventinfo.EventInfoFragment
import com.example.nishant.fenrir.screens.mainapp.events.eventlist.EventListFragment
import com.example.nishant.fenrir.screens.mainapp.more.MoreFragment

abstract class NavHostActivity : AppCompatActivity(), NavigationHost {

    abstract val navViewId: Int

    override fun show(destination: Destination, bundle: Bundle?) {
        if(destination == NavigationGraph.MainApp.MAIN_APP) {
            startActivity(Intent(this, MainAppActivity::class.java))
            finish()
        }
        else {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
                    .setCustomAnimations(destination.ei, destination.eo, destination.pi, destination.po)
                    .replace(navViewId, getFragmentByDestination(destination).withBundle(bundle))

            when(destination.isTopLevel) {
                true  -> clearBackStack()
                false -> fragmentTransaction.addToBackStack(null)
            }

            fragmentTransaction.commit()
        }
    }

    private fun getFragmentByDestination(destination: Destination): Fragment {
        return when(destination) {
            NavigationGraph.MainApp.Events.EVENT_LIST -> EventListFragment()
            NavigationGraph.MainApp.Events.EVENT_INFO -> EventInfoFragment()
            NavigationGraph.MainApp.More.MORE         -> MoreFragment()
            else                                      -> throw IllegalArgumentException("Can't navigate to ${destination.name}")
        }
    }

    private fun clearBackStack() {
        for(i in 0..supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun back(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }

    override fun onBackPressed() {
        if(supportFragmentManager.fragments.size > 0) {
            with(supportFragmentManager.fragments.last()) {
                if(!(this is NavHostFragment && this.back())) {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun exit() {
        onBackPressed()
    }
}