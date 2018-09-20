package com.example.nishant.fenrir.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.nishant.fenrir.events.eventfilter.eventfilterlist.EventFilterListFragment
import com.example.nishant.fenrir.events.eventfilter.eventfiltermenu.EventFilterMenuFragment

abstract class NavHostFragment : Fragment(), NavigationHost {

    abstract val navViewId: Int

    override fun show(destination: Destination, bundle: Bundle?) {
        val fragment = when(destination) {
            NavigationGraph.Events.EventFilter.FILTER_MENU -> EventFilterMenuFragment().withBundle(bundle)
            NavigationGraph.Events.EventFilter.FILTER_LIST -> EventFilterListFragment().withBundle(bundle)
            else                                           -> throw IllegalArgumentException("Can't navigate to ${destination.name}")
        }
        childFragmentManager.beginTransaction()
                .setCustomAnimations(destination.ei, destination.eo, destination.pi, destination.po)
                .replace(navViewId, fragment, destination.name)
                .addToBackStack(null)
                .commit()
    }

    override fun back(): Boolean {
        if(childFragmentManager.backStackEntryCount == 0) {
            return false
        }
        if(childFragmentManager.backStackEntryCount == 1) {
            exit()
            return true
        }
        childFragmentManager.popBackStack()
        return true
    }

    override fun exit() {
        for(i in 0..childFragmentManager.backStackEntryCount) {
            childFragmentManager.popBackStack()
        }
    }
}