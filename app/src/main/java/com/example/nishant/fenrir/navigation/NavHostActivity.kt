package com.example.nishant.fenrir.navigation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nishant.fenrir.events.eventinfo.EventInfoFragment
import com.example.nishant.fenrir.events.eventlist.EventListFragment

abstract class NavHostActivity : AppCompatActivity(), NavigationHost {

    abstract val navViewId: Int

    override fun show(destination: Destination, bundle: Bundle?) {
        if(destination.isTopLevel) {
            clearBackStack()
            val fragment = when(destination) {
                NavigationGraph.Events.EVENT_LIST -> EventListFragment().withBundle(bundle)
                else                              -> throw IllegalArgumentException("Can't navigate to ${destination.name}")
            }
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(destination.ei, destination.eo, destination.pi, destination.po)
                    .replace(navViewId, fragment)
                    .commit()
        }
        else {
            val fragment = when(destination) {
                NavigationGraph.Events.EVENT_INFO -> EventInfoFragment().withBundle(bundle)
                else                              -> throw IllegalArgumentException("Can't navigate to ${destination.name}")
            }
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(destination.ei, destination.eo, destination.pi, destination.po)
                    .replace(navViewId, fragment)
                    .addToBackStack(null)
                    .commit()
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